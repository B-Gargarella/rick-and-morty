package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Character
import com.bgargarella.ram.domain.model.Result
import com.bgargarella.ram.domain.repository.InfoRepository

class GetCharactersUseCase(
    private val repository: InfoRepository
) {

    suspend operator fun invoke(ids: List<Int>): Result<List<Character>> =
        repository.getCharacters(ids = ids)
}