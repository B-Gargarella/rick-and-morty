package com.bgargarella.ram.data.location.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.base.repository.BaseRepositoryImpl
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.data.location.mapper.toLocation
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class LocationRepositoryImpl(
    private val db: RamDB,
    private val service: APIService,
) : BaseRepositoryImpl(), LocationRepository {

    override fun getLocation(id: Int): Flow<Location> =
        db.locationDao().get(id).transform { it.toLocation() }

    override fun getLocations(): Flow<PagingData<Location>> = getPager()

    override fun getLocationsByIds(ids: List<Int>): Flow<PagingData<Location>> = getPager(ids)

    @OptIn(ExperimentalPagingApi::class)
    fun getPager(ids: List<Int>? = null): Flow<PagingData<Location>> =
        Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = LocationRemoteMediator(
                db = db,
                service = service,
                ids = ids
            ),
            pagingSourceFactory = { db.locationDao().getAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toLocation() }
        }
}