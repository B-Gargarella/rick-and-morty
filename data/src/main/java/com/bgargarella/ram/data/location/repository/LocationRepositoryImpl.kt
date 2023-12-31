package com.bgargarella.ram.data.location.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.base.repository.BaseRepositoryImpl
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.data.location.mapper.toLocation
import com.bgargarella.ram.data.location.mapper.toLocationModel
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.domain.location.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val db: RamDB,
    private val service: APIService,
) : BaseRepositoryImpl(context), LocationRepository {

    override fun getLocation(id: Int): Flow<Result<Location>> =
        getEntity(
            getLocal = { db.locationDao().get(id) },
            getRemote = { service.getLocation(id) },
            getData = { it.toLocationModel() },
            saveLocal = db.locationDao()::save,
            getDomain = { it.toLocation() },
        )

    @OptIn(ExperimentalPagingApi::class)
    override fun getLocations(): Flow<PagingData<Location>> =
        Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = LocationRemoteMediator(
                db = db,
                service = service,
            ),
            pagingSourceFactory = { db.locationDao().getAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toLocation() }
        }

    override suspend fun getLocations(ids: String): List<Location> =
        service.getLocations(ids).body().orEmpty().map { it.toLocationModel().toLocation() }
}