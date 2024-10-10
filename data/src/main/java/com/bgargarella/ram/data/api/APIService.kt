package com.bgargarella.ram.data.api

import com.bgargarella.ram.data.entity.base.model.BasePageResponse
import com.bgargarella.ram.data.entity.character.model.CharacterResponse
import com.bgargarella.ram.data.entity.episode.model.EpisodeResponse
import com.bgargarella.ram.data.entity.location.model.LocationResponse
import com.bgargarella.ram.data.util.CHARACTER
import com.bgargarella.ram.data.util.EPISODE
import com.bgargarella.ram.data.util.LOCATION
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET(CHARACTER)
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<BasePageResponse<CharacterResponse>>

    @GET("$CHARACTER/{ids}")
    suspend fun getCharacters(
        @Path("ids") ids: String
    ): Response<List<CharacterResponse>>

    @GET("$CHARACTER/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): Response<CharacterResponse>

    @GET(EPISODE)
    suspend fun getEpisodes(
        @Query("page") page: Int
    ): Response<BasePageResponse<EpisodeResponse>>

    @GET("$EPISODE/{ids}")
    suspend fun getEpisodes(
        @Path("ids") ids: String
    ): Response<List<EpisodeResponse>>

    @GET("$EPISODE/{id}")
    suspend fun getEpisode(
        @Path("id") id: Int
    ): Response<EpisodeResponse>

    @GET(LOCATION)
    suspend fun getLocations(
        @Query("page") page: Int
    ): Response<BasePageResponse<LocationResponse>>

    @GET("$LOCATION/{ids}")
    suspend fun getLocations(
        @Path("ids") ids: String
    ): Response<List<LocationResponse>>

    @GET("$LOCATION/{id}")
    suspend fun getLocation(
        @Path("id") id: Int
    ): Response<LocationResponse>
}