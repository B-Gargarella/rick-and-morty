package com.bgargarella.ram.data.api

import com.bgargarella.ram.data.base.model.BaseResponse
import com.bgargarella.ram.data.character.model.CharacterResponse
import com.bgargarella.ram.data.episode.model.EpisodeResponse
import com.bgargarella.ram.data.location.model.LocationResponse
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.location.model.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
    ): Response<BaseResponse<CharacterResponse>>

    @GET("character/{ids}")
    suspend fun getCharacters(
        @Path("ids") ids: List<Int>,
    ): Response<List<Character>>

    @GET("character")
    suspend fun getCharacter(
        @Query("id") id: Int,
    ): Response<Character>

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int,
    ): Response<BaseResponse<EpisodeResponse>>

    @GET("episode/{ids}")
    suspend fun getEpisodes(
        @Path("ids") ids: List<Int>,
    ): Response<List<Episode>>

    @GET("episode")
    suspend fun getEpisode(
        @Query("id") id: Int,
    ): Response<Episode>

    @GET("location")
    suspend fun getLocations(
        @Query("page") page: Int
    ): Response<BaseResponse<LocationResponse>>

    @GET("location/{ids}")
    suspend fun getLocations(
        @Path("ids") ids: List<Int>,
    ): Response<List<Location>>

    @GET("location")
    suspend fun getLocation(
        @Query("id") id: Int
    ): Response<Location>
}