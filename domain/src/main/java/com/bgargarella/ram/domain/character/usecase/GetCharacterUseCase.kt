package com.bgargarella.ram.domain.character.usecase

import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharacterUseCase(private val repository: CharacterRepository) {

    operator fun invoke(id: Int): Flow<Result<Character>> = repository.getCharacter(id)
}