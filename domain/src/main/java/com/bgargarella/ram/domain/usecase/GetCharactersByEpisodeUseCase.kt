package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Character
import com.bgargarella.ram.domain.repository.BaseEntityAttributesRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersByEpisodeUseCase(
    private val repository: BaseEntityAttributesRepository<Character>
) {

    suspend operator fun invoke(id: Int): Flow<List<Character>> =
        repository.getEntityAttributes(id = id)
}