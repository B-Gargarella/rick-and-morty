package com.bgargarella.ram.domain.character.usecase

import androidx.paging.PagingData
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(private val repository: CharacterRepository) {

    operator fun invoke(): Flow<PagingData<Character>> = repository.getCharacters()
}