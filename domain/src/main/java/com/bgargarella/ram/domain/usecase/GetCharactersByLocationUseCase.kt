package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Character
import com.bgargarella.ram.domain.model.Result
import com.bgargarella.ram.domain.repository.InfoRepository

class GetCharactersByLocationUseCase(
    private val repository: InfoRepository
) {

    suspend operator fun invoke(id: Int): Result<List<Character>> =
        repository.getLocationCharacters(id = id)
}