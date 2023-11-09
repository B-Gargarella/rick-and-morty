package com.bgargarella.ram.data.episode.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.MediatorResult.Error
import androidx.paging.RemoteMediator.MediatorResult.Success
import androidx.room.withTransaction
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.base.model.BaseResponse
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.data.episode.mapper.toEpisodeModel
import com.bgargarella.ram.data.episode.model.EpisodeModel
import com.bgargarella.ram.data.episode.model.EpisodeResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

@OptIn(ExperimentalPagingApi::class)
class EpisodeRemoteMediator(
    private val db: RamDB,
    private val service: APIService,
) : RemoteMediator<Int, EpisodeModel>() {

    private val STARTING_PAGE_INDEX: Int = 1

    override suspend fun initialize(): InitializeAction =
        InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeModel>
    ): MediatorResult {
        val page = when (loadType) {
            REFRESH -> STARTING_PAGE_INDEX
            PREPEND -> return Success(endOfPaginationReached = false)
            APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    STARTING_PAGE_INDEX
                } else {
                    (lastItem.id / state.config.pageSize) + 1
                }
            }
        }

        return try {
            val response: Response<BaseResponse<EpisodeResponse>> =
                service.getEpisodes(page = page)

            val body: BaseResponse<EpisodeResponse>? = response.body()

            val entities: List<EpisodeResponse> = body?.results.orEmpty()

            val endOfPaginationReached: Boolean = body?.info?.next == null

            db.withTransaction {
                db.episodeDao().apply {
                    saveAll(entities.map { it.toEpisodeModel() })
                }
            }

            Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: SocketTimeoutException) {
            Error(e)
        } catch (e: IOException) {
            Error(e)
        } catch (e: HttpException) {
            Error(e)
        }
    }
}