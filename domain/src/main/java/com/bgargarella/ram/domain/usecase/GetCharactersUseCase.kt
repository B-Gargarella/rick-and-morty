package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Character
import com.bgargarella.ram.domain.repository.BaseEntityRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(
    private val repository: BaseEntityRepository<Character>
) {

    suspend operator fun invoke(ids: List<Int>): Flow<List<Character>> =
        repository.getEntities(ids = ids)
}