package com.bgargarella.ram.data.character.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.base.repository.BaseRepositoryImpl
import com.bgargarella.ram.data.character.mapper.toCharacter
import com.bgargarella.ram.data.character.mapper.toCharacterModel
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class CharacterRepositoryImpl(
    private val db: RamDB,
    private val service: APIService,
) : BaseRepositoryImpl(), CharacterRepository {

    override fun getCharacter(id: Int): Flow<Character> =
        flow {
            emit(service.getCharacter(id))
        }
            .transform { response ->
                response.body()?.toCharacterModel()?.let { model ->
                    db.characterDao().save(model)
                    emit(model.toCharacter())
                }
            }

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = CharacterRemoteMediator(
                db = db,
                service = service,
            ),
            pagingSourceFactory = { db.characterDao().getAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toCharacter() }
        }

    override suspend fun getCharacters(ids: List<Int>): List<Character> =
        service.getCharacters(ids).body().orEmpty().map { it.toCharacterModel().toCharacter() }
}