package com.bgargarella.ram.domain.repository

import com.bgargarella.ram.domain.entities.Character
import com.bgargarella.ram.domain.entities.Episode
import com.bgargarella.ram.domain.entities.Location
import com.bgargarella.ram.domain.model.Result

interface InfoRepository {

    suspend fun getCharacter(id: Int): Result<Character>

    suspend fun getCharactersPage(page: Int): Result<List<Character>>

    suspend fun getCharacters(ids: List<Int>): Result<List<Character>>

    suspend fun getCharacterEpisodes(id: Int): Result<List<Episode>>

    suspend fun getEpisode(id: Int): Result<Episode>

    suspend fun getEpisodesPage(page: Int): Result<List<Episode>>

    suspend fun getEpisodes(ids: List<Int>): Result<List<Episode>>

    suspend fun getEpisodeCharacters(id: Int): Result<List<Character>>

    suspend fun getLocation(id: Int): Result<Location>

    suspend fun getLocationsPage(page: Int): Result<List<Location>>

    suspend fun getLocations(ids: List<Int>): Result<List<Location>>

    suspend fun getLocationCharacters(id: Int): Result<List<Character>>
}