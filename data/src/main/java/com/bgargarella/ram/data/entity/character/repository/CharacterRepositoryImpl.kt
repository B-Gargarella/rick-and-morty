package com.bgargarella.ram.data.entity.character.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.entity.base.repository.BaseRepositoryImpl
import com.bgargarella.ram.data.entity.character.mapper.toEntity
import com.bgargarella.ram.data.entity.character.mapper.toModel
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val db: RamDB,
    private val service: APIService
) : BaseRepositoryImpl(context), CharacterRepository {

    override fun getCharacter(id: Int): Flow<Result<Character>> =
        getEntity(
            getLocal = { db.characterDao().get(id) },
            getRemote = { service.getCharacter(id) },
            getData = { it.toModel() },
            saveLocal = db.characterDao()::save,
            getDomain = { it.toEntity() }
        )

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(): Flow<PagingData<Character>> =
        Pager(
            config = pagingConfig,
            remoteMediator = CharacterRemoteMediator(
                db = db,
                service = service
            ),
            pagingSourceFactory = { db.characterDao().getAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toEntity() }
        }

    override suspend fun getCharacters(ids: String): List<Character> =
        getEntities(
            ids = ids,
            singleEntity = {
                service
                    .getCharacter(ids.toInt())
                    .body()
                    ?.toModel()
                    ?.toEntity()
            },
            multipleEntities = {
                service
                    .getCharacters(ids)
                    .body()
                    .orEmpty()
                    .map { it.toModel().toEntity() }
            }
        )
}