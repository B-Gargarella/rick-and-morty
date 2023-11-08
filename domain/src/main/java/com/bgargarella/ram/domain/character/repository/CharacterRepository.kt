package com.bgargarella.ram.domain.character.repository

import androidx.paging.PagingData
import com.bgargarella.ram.domain.character.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacter(id: Int): Flow<Character>

    fun getCharacterTest(id: Int): Flow<Character?>

    fun getCharacters(): Flow<PagingData<Character>>

    fun getCharacters(ids: List<Int>): Flow<PagingData<Character>>
}