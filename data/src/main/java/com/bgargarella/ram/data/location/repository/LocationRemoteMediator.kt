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
import com.bgargarella.ram.data.base.model.BaseResponse.Info
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
    private val ids: List<Int>? = null,
) : RemoteMediator<Int, LocationModel>() {

    override suspend fun initialize(): InitializeAction =
        InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocationModel>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                REFRESH -> 1
                PREPEND -> return Success(endOfPaginationReached = true)
                APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val response: Response<BaseResponse<LocationResponse>> =
                if (ids.isNullOrEmpty()) {
                    service.getLocations(page = loadKey)
                } else {
                    service.getLocations(ids = ids, page = loadKey)
                }

            val body: BaseResponse<LocationResponse>? = response.body()
            val info: Info? = body?.info
            val endOfPaginationReached: Boolean = info?.next == null

            val entities: List<LocationModel> =
                body?.results.orEmpty().map { it.toLocationModel() }

            db.withTransaction {
                if (loadType == REFRESH) {
                    db.locationDao().deleteAll()
                }
                db.locationDao().saveAll(entities)
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