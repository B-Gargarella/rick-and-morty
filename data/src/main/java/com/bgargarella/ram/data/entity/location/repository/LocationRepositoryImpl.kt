package com.bgargarella.ram.data.entity.location.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.entity.base.repository.BaseRepositoryImpl
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.data.entity.location.mapper.toEntity
import com.bgargarella.ram.data.entity.location.mapper.toModel
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.domain.location.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val db: RamDB,
    private val service: APIService
) : BaseRepositoryImpl(context), LocationRepository {

    override fun getLocation(id: Int): Flow<Result<Location>> =
        getEntity(
            getLocal = { db.locationDao().get(id) },
            getRemote = { service.getLocation(id) },
            getData = { it.toModel() },
            saveLocal = db.locationDao()::save,
            getDomain = { it.toEntity() }
        )

    @OptIn(ExperimentalPagingApi::class)
    override fun getLocations(): Flow<PagingData<Location>> =
        Pager(
            config = pagingConfig,
            remoteMediator = LocationRemoteMediator(
                db = db,
                service = service
            ),
            pagingSourceFactory = { db.locationDao().getAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toEntity() }
        }

    override suspend fun getLocations(ids: String): List<Location> =
        getEntities(
            ids = ids,
            singleEntity = {
                service
                    .getLocation(ids.toInt())
                    .body()
                    ?.toModel()
                    ?.toEntity()
            },
            multipleEntities = {
                service
                    .getLocations(ids)
                    .body()
                    .orEmpty()
                    .map { it.toModel().toEntity() }
            }
        )
}