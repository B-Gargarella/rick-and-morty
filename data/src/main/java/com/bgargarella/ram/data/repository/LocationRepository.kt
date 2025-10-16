package com.bgargarella.ram.data.repository

import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.domain.entities.Character
import com.bgargarella.ram.domain.entities.Location
import com.bgargarella.ram.domain.repository.BaseEntityAttributesRepository
import com.bgargarella.ram.domain.repository.BaseEntityRepository
import kotlinx.coroutines.flow.Flow

class LocationRepository(
    private val db: RamDB,
    private val service: APIService
) : BaseEntityRepository<Location>,
    BaseEntityAttributesRepository<Character>,
    BaseRepository() {

    override suspend fun getEntity(id: Int): Flow<Location> =
        getEntity(
            getRemote = { service.getLocation(id = id) },
            saveLocal = db.locationDao()::save,
            getLocal = { db.locationDao().get(id = id) },
            toModel = { toDomain() }
        )

    override suspend fun getEntitiesPage(page: Int): Flow<List<Location>> =
        getPagedEntities(
            getRemote = { service.getLocationsPage(page = page) },
            saveLocal = db.locationDao()::save,
            getLocal = db.locationDao()::get,
            toModel = { toDomain() }
        )

    override suspend fun getEntities(ids: List<Int>): Flow<List<Location>> =
        getEntities(
            getRemote = { service.getLocations(ids = ids) },
            saveLocal = db.locationDao()::save,
            getLocal = { db.locationDao().get(ids = ids) },
            toModel = { toDomain() }
        )

    override suspend fun getEntityAttributes(id: Int): Flow<List<Character>> =
        getEntityAttributes(
            getRemote = { service.getLocation(id = id) },
            saveLocalEntity = db.locationDao()::save,
            toAttributeIds = { residents },
            getRemoteAttributes = service::getCharacters,
            saveLocalAttributes = db.characterDao()::save,
            getLocalAttributes = db.characterDao()::get,
            toAttribute = { toDomain() }
        )
}