package com.bgargarella.ram.data.episode.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.base.repository.BaseRepositoryImpl
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.data.episode.mapper.toEpisode
import com.bgargarella.ram.data.episode.mapper.toEpisodeModel
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EpisodeRepositoryImpl(
    private val db: RamDB,
    private val service: APIService,
) : BaseRepositoryImpl(), EpisodeRepository {

    override fun getEpisode(id: Int): Flow<Result<Episode>> =
        getEntity(
            getLocal = { db.episodeDao().get(id) },
            getRemote = { service.getEpisode(id) },
            mappingDataAction = { it.toEpisodeModel() },
            saveLocal = db.episodeDao()::save,
            mappingDomainAction = { it.toEpisode() },
        )

    @OptIn(ExperimentalPagingApi::class)
    override fun getEpisodes(): Flow<PagingData<Episode>> =
        Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = EpisodeRemoteMediator(
                db = db,
                service = service,
            ),
            pagingSourceFactory = { db.episodeDao().getAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toEpisode() }
        }

    override suspend fun getEpisodes(ids: List<Int>): List<Episode> =
        service.getEpisodes(ids).body().orEmpty().map { it.toEpisodeModel().toEpisode() }
}