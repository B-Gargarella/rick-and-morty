package com.bgargarella.ram.data.character.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.base.repository.BaseRepositoryImpl
import com.bgargarella.ram.data.character.mapper.toCharacter
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(
    private val db: RamDB,
    private val service: APIService,
) : BaseRepositoryImpl(), CharacterRepository {

    override fun getCharacter(id: Int): Flow<Character> =
        flow { emit(db.characterDao().get(id).toCharacter()) }

    override fun getCharacterTest(id: Int): Flow<Character?> =
        db.characterDao().getTest(id).map { it?.toCharacter() }

    override fun getCharacters(): Flow<PagingData<Character>> = getPager()

    override fun getCharacters(ids: List<Int>): Flow<PagingData<Character>> = getPager(ids = ids)

    @OptIn(ExperimentalPagingApi::class)
    private fun getPager(ids: List<Int>? = null): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = CharacterRemoteMediator(
                db = db,
                service = service,
                ids = ids,
            ),
            pagingSourceFactory = { db.characterDao().getAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toCharacter() }
        }
}