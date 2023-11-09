package com.bgargarella.ram.domain.episode.repository

import androidx.paging.PagingData
import com.bgargarella.ram.domain.episode.model.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun getEpisode(id: Int): Flow<Episode>

    fun getEpisodes(): Flow<PagingData<Episode>>

    suspend fun getEpisodes(ids: List<Int>): List<Episode>
}