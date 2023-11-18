package com.bgargarella.ram.domain.character.repository

import androidx.paging.PagingData
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.character.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacter(id: Int): Flow<Result<Character>>

    fun getCharacters(): Flow<PagingData<Character>>

    suspend fun getCharacters(ids: String): List<Character>
}