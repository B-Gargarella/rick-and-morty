package com.bgargarella.ram.domain.episode.usecase

import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodeUseCase(private val repository: EpisodeRepository) {

    operator fun invoke(id: Int): Flow<Result<Episode>> = repository.getEpisode(id)
}