package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Character
import com.bgargarella.ram.domain.repository.BaseEntityRepository
import kotlinx.coroutines.flow.Flow

class GetCharacterUseCase(
    private val repository: BaseEntityRepository<Character>
) {

    suspend operator fun invoke(id: Int): Flow<Character> =
        repository.getEntity(id = id)
}