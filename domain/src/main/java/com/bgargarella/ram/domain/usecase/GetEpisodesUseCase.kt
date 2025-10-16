package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Episode
import com.bgargarella.ram.domain.repository.BaseEntityRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodesUseCase(
    private val repository: BaseEntityRepository<Episode>
) {

    suspend operator fun invoke(ids: List<Int>): Flow<List<Episode>> =
        repository.getEntities(ids = ids)
}