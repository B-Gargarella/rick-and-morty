package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Episode
import com.bgargarella.ram.domain.repository.BaseEntityAttributesRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodesByCharacterUseCase(
    private val repository: BaseEntityAttributesRepository<Episode>
) {

    suspend operator fun invoke(id: Int): Flow<List<Episode>> =
        repository.getEntityAttributes(id = id)
}