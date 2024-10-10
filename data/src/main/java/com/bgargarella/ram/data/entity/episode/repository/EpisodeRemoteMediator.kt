package com.bgargarella.ram.data.entity.episode.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.entity.base.model.BasePageResponse
import com.bgargarella.ram.data.entity.base.repository.BaseRemoteMediator
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.data.entity.episode.mapper.toModel
import com.bgargarella.ram.data.entity.episode.model.EpisodeModel
import com.bgargarella.ram.data.entity.episode.model.EpisodeResponse
import retrofit2.Response

@OptIn(ExperimentalPagingApi::class)
class EpisodeRemoteMediator(
    private val db: RamDB,
    private val service: APIService,
) : RemoteMediator<Int, EpisodeModel>(), BaseRemoteMediator<EpisodeResponse> {

    override suspend fun initialize(): InitializeAction =
        super<BaseRemoteMediator>.initialize()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeModel>,
    ): MediatorResult = loadMediatorResult(loadType, state)

    override suspend fun getResponse(page: Int): Response<BasePageResponse<EpisodeResponse>> =
        service.getEpisodes(page = page)

    override suspend fun saveResponse(
        loadType: LoadType,
        response: Response<BasePageResponse<EpisodeResponse>>,
    ) {
        db.apply {
            withTransaction {
                episodeDao().apply {
                    if (loadType == REFRESH) {
                        deleteAll()
                    }
                    saveAll(response.getResults { it.toModel() })
                }
            }
        }
    }
}