package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Episode
import com.bgargarella.ram.domain.model.Result
import com.bgargarella.ram.domain.repository.InfoRepository

class GetEpisodesUseCase(
    private val repository: InfoRepository
) {

    suspend operator fun invoke(ids: List<Int>): Result<List<Episode>> =
        repository.getEpisodes(ids = ids)
}