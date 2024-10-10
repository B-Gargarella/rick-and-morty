package com.bgargarella.ram.data.entity.location.repository

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
import com.bgargarella.ram.data.entity.location.mapper.toModel
import com.bgargarella.ram.data.entity.location.model.LocationModel
import com.bgargarella.ram.data.entity.location.model.LocationResponse
import retrofit2.Response

@OptIn(ExperimentalPagingApi::class)
class LocationRemoteMediator(
    private val db: RamDB,
    private val service: APIService,
) : RemoteMediator<Int, LocationModel>(), BaseRemoteMediator<LocationResponse> {

    override suspend fun initialize(): InitializeAction =
        super<BaseRemoteMediator>.initialize()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocationModel>,
    ): MediatorResult = loadMediatorResult(loadType, state)

    override suspend fun getResponse(page: Int): Response<BasePageResponse<LocationResponse>> =
        service.getLocations(page = page)

    override suspend fun saveResponse(
        loadType: LoadType,
        response: Response<BasePageResponse<LocationResponse>>,
    ) {
        db.apply {
            withTransaction {
                locationDao().apply {
                    if (loadType == REFRESH) {
                        deleteAll()
                    }
                    saveAll(response.getResults { it.toModel() })
                }
            }
        }
    }
}