package com.bgargarella.ram.data.episode.repository

import android.util.Log
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
import com.bgargarella.ram.data.base.model.BaseResponse.Info
import com.bgargarella.ram.data.character.mapper.toCharacterModel
import com.bgargarella.ram.data.character.model.CharacterModel
import com.bgargarella.ram.data.character.model.CharacterResponse
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
    private val ids: List<Int>,
) : RemoteMediator<Int, EpisodeModel>() {

    private val STARTING_PAGE_INDEX: Int = 1

    override suspend fun initialize(): InitializeAction =
        InitializeAction.SKIP_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeModel>
    ): MediatorResult {
        val page = when (loadType) {
            REFRESH -> {
                Log.e("loadType", "REFRESH")
                STARTING_PAGE_INDEX
            }
            PREPEND -> {
                Log.e("loadType", "PREPEND")
                return Success(endOfPaginationReached = false)
            }

            APPEND -> {
                Log.e("loadType", "APPEND")
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    STARTING_PAGE_INDEX
                } else {
                    (lastItem.id / state.config.pageSize) + 1
                }
            }
        }

        return try {
            val response: Response<List<EpisodeResponse>> =
                service.getEpisodes(ids = ids)

            val entities: List<EpisodeResponse> = response.body().orEmpty()

            val endOfPaginationReached = page == STARTING_PAGE_INDEX

            db.withTransaction {
                db.episodeDao().apply {
                    if (loadType == REFRESH) {
                        deleteAll()
                    }
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