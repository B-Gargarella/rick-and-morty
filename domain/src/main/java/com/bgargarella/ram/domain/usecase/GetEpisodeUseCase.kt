package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Episode
import com.bgargarella.ram.domain.repository.BaseEntityRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodeUseCase(
    private val repository: BaseEntityRepository<Episode>
) {

    suspend operator fun invoke(id: Int): Flow<Episode> =
        repository.getEntity(id = id)
}