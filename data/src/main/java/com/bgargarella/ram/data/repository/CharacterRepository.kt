package com.bgargarella.ram.data.repository

import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.data.entity.CharacterEntity
import com.bgargarella.ram.domain.entities.Character
import com.bgargarella.ram.domain.entities.Episode
import com.bgargarella.ram.domain.repository.BaseEntityAttributesRepository
import com.bgargarella.ram.domain.repository.BaseEntityRepository
import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val db: RamDB,
    private val service: APIService
) : BaseEntityRepository<Character>,
    BaseEntityAttributesRepository<Episode>,
    BaseRepository() {

    override suspend fun getEntity(id: Int): Flow<Character> =
        getEntity<CharacterEntity, Character>(
            getRemote = { service.getCharacter(id = id) },
            saveLocal = db.characterDao()::save,
            getLocal = { db.characterDao().get(id = id) },
            toModel = { toDomain() }
        )

    override suspend fun getEntitiesPage(page: Int): Flow<List<Character>> =
        getPagedEntities<CharacterEntity, Character>(
            getRemote = { service.getCharactersPage(page = page) },
            saveLocal = db.characterDao()::save,
            getLocal = { db.characterDao().get(ids = it) },
            toModel = { toDomain() }
        )

    override suspend fun getEntities(ids: List<Int>): Flow<List<Character>> =
        getEntities<CharacterEntity, Character>(
            getRemote = { service.getCharacters(ids = ids) },
            saveLocal = db.characterDao()::save,
            getLocal = { db.characterDao().get(ids = ids) },
            toModel = { toDomain() }
        )

    override suspend fun getEntityAttributes(id: Int): Flow<List<Episode>> =
        getEntityAttributes(
            getRemote = { service.getCharacter(id = id) },
            saveLocalEntity = db.characterDao()::save,
            toAttributeIds = { episodes },
            getRemoteAttributes = service::getEpisodes,
            saveLocalAttributes = db.episodeDao()::save,
            getLocalAttributes = db.episodeDao()::get,
            toAttribute = { toDomain() }
        )
}