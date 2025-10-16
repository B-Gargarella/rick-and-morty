package com.bgargarella.ram.data.api

import com.bgargarella.ram.data.dto.CharacterDTO
import com.bgargarella.ram.data.dto.EpisodeDTO
import com.bgargarella.ram.data.dto.LocationDTO
import com.bgargarella.ram.data.entity.CharacterEntity
import com.bgargarella.ram.data.entity.EpisodeEntity
import com.bgargarella.ram.data.entity.LocationEntity
import com.bgargarella.ram.data.entity.PageResponse
import com.bgargarella.ram.data.util.CHARACTER
import com.bgargarella.ram.data.util.EPISODE
import com.bgargarella.ram.data.util.LOCATION

class APIService() : BaseAPIService() {

    suspend fun getCharactersPage(page: Int): PageResponse<CharacterEntity> =
        getEndpointPage<CharacterDTO, CharacterEntity>(
            endpoint = CHARACTER,
            page = page,
            action = { it.toModel() }
        )

    suspend fun getCharacters(ids: List<Int>): List<CharacterEntity> =
        getEndpointIds<CharacterDTO, CharacterEntity>(
            endpoint = CHARACTER,
            ids = ids,
            action = { it.toModel() }
        )

    suspend fun getCharacter(id: Int): CharacterEntity =
        getEndpointId<CharacterDTO, CharacterEntity>(
            endpoint = CHARACTER,
            id = id,
            action = { it.toModel() }
        )

    suspend fun getCharacterEpisodes(id: Int): List<EpisodeEntity> =
        getEpisodes(ids = getCharacter(id = id).episodes)

    suspend fun getEpisodesPage(page: Int): PageResponse<EpisodeEntity> =
        getEndpointPage<EpisodeDTO, EpisodeEntity>(
            endpoint = EPISODE,
            page = page,
            action = { it.toModel() }
        )

    suspend fun getEpisodes(ids: List<Int>): List<EpisodeEntity> =
        getEndpointIds<EpisodeDTO, EpisodeEntity>(
            endpoint = EPISODE,
            ids = ids,
            action = { it.toModel() }
        )

    suspend fun getEpisode(id: Int): EpisodeEntity =
        getEndpointId<EpisodeDTO, EpisodeEntity>(
            endpoint = EPISODE,
            id = id,
            action = { it.toModel() }
        )

    suspend fun getEpisodeCharacters(id: Int): List<CharacterEntity> =
        getCharacters(ids = getEpisode(id = id).characters)

    suspend fun getLocationsPage(page: Int): PageResponse<LocationEntity> =
        getEndpointPage<LocationDTO, LocationEntity>(
            endpoint = LOCATION,
            page = page,
            action = { it.toModel() }
        )

    suspend fun getLocations(ids: List<Int>): List<LocationEntity> =
        getEndpointIds<LocationDTO, LocationEntity>(
            endpoint = LOCATION,
            ids = ids,
            action = { it.toModel() }
        )

    suspend fun getLocation(id: Int): LocationEntity =
        getEndpointId<LocationDTO, LocationEntity>(
            endpoint = LOCATION,
            id = id,
            action = { it.toModel() }
        )

    suspend fun getLocationCharacters(id: Int): List<CharacterEntity> =
        getCharacters(ids = getLocation(id = id).residents)
}