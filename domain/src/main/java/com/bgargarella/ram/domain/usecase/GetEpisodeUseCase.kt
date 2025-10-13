package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Episode
import com.bgargarella.ram.domain.model.Result
import com.bgargarella.ram.domain.repository.InfoRepository

class GetEpisodeUseCase(
    private val repository: InfoRepository
) {

    suspend operator fun invoke(id: Int): Result<Episode> =
        repository.getEpisode(id = id)
}