package com.bgargarella.ram.data.location.repository

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
import com.bgargarella.ram.data.location.mapper.toLocationModel
import com.bgargarella.ram.data.location.model.LocationModel
import com.bgargarella.ram.data.location.model.LocationResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

@OptIn(ExperimentalPagingApi::class)
class LocationRemoteMediator(
    private val db: RamDB,
    private val service: APIService,
) : RemoteMediator<Int, LocationModel>() {

    private val STARTING_PAGE_INDEX: Int = 1

    override suspend fun initialize(): InitializeAction =
        InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocationModel>
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
            val response: Response<BaseResponse<LocationResponse>> =
                service.getLocations(page = page)

            val body: BaseResponse<LocationResponse>? = response.body()

            val entities: List<LocationResponse> = body?.results.orEmpty()

            val endOfPaginationReached: Boolean = body?.info?.next == null

            db.withTransaction {
                db.locationDao().apply {
                    saveAll(entities.map { it.toLocationModel() })
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