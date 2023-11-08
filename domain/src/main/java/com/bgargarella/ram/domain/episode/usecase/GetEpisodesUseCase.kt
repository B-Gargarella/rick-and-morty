package com.bgargarella.ram.domain.episode.usecase

import androidx.paging.PagingData
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodesUseCase(private val repository: EpisodeRepository) {

    operator fun invoke(): Flow<PagingData<Episode>> = repository.getEpisodes()
}