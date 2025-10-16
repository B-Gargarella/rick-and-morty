package com.bgargarella.ram.data.repository

import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.domain.entities.Character
import com.bgargarella.ram.domain.entities.Episode
import com.bgargarella.ram.domain.repository.BaseEntityAttributesRepository
import com.bgargarella.ram.domain.repository.BaseEntityRepository
import kotlinx.coroutines.flow.Flow

class EpisodeRepository(
    private val db: RamDB,
    private val service: APIService
) : BaseEntityRepository<Episode>,
    BaseEntityAttributesRepository<Character>,
    BaseRepository() {

    override suspend fun getEntity(id: Int): Flow<Episode> =
        getEntity(
            getRemote = { service.getEpisode(id = id) },
            saveLocal = db.episodeDao()::save,
            getLocal = { db.episodeDao().get(id = id) },
            toModel = { toDomain() }
        )

    override suspend fun getEntitiesPage(page: Int): Flow<List<Episode>> =
        getPagedEntities(
            getRemote = { service.getEpisodesPage(page = page) },
            saveLocal = db.episodeDao()::save,
            getLocal = db.episodeDao()::get,
            toModel = { toDomain() }
        )

    override suspend fun getEntities(ids: List<Int>): Flow<List<Episode>> =
        getEntities(
            getRemote = { service.getEpisodes(ids = ids) },
            saveLocal = db.episodeDao()::save,
            getLocal = { db.episodeDao().get(ids = ids) },
            toModel = { toDomain() }
        )

    override suspend fun getEntityAttributes(id: Int): Flow<List<Character>> =
        getEntityAttributes(
            getRemote = { service.getEpisode(id = id) },
            saveLocalEntity = db.episodeDao()::save,
            toAttributeIds = { characters },
            getRemoteAttributes = service::getCharacters,
            saveLocalAttributes = db.characterDao()::save,
            getLocalAttributes = db.characterDao()::get,
            toAttribute = { toDomain() }
        )
}