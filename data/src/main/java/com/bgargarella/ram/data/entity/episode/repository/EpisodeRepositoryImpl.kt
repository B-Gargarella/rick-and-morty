package com.bgargarella.ram.data.entity.episode.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.entity.base.repository.BaseRepositoryImpl
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.data.entity.episode.mapper.toEntity
import com.bgargarella.ram.data.entity.episode.mapper.toModel
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EpisodeRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val db: RamDB,
    private val service: APIService,
) : BaseRepositoryImpl(context), EpisodeRepository {

    override fun getEpisode(id: Int): Flow<Result<Episode>> =
        getEntity(
            getLocal = { db.episodeDao().get(id) },
            getRemote = { service.getEpisode(id) },
            getData = { it.toModel() },
            saveLocal = db.episodeDao()::save,
            getDomain = { it.toEntity() },
        )

    @OptIn(ExperimentalPagingApi::class)
    override fun getEpisodes(): Flow<PagingData<Episode>> =
        Pager(
            config = pagingConfig,
            remoteMediator = EpisodeRemoteMediator(
                db = db,
                service = service,
            ),
            pagingSourceFactory = { db.episodeDao().getAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toEntity() }
        }

    override suspend fun getEpisodes(ids: String): List<Episode> =
        getEntities(
            ids = ids,
            singleEntity = {
                service
                    .getEpisode(ids.toInt())
                    .body()
                    ?.toModel()
                    ?.toEntity()
            },
            multipleEntities = {
                service
                    .getEpisodes(ids)
                    .body()
                    .orEmpty()
                    .map { it.toModel().toEntity() }
            }
        )
}