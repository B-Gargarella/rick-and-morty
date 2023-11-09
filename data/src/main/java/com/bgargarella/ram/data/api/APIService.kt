package com.bgargarella.ram.data.api

import com.bgargarella.ram.data.base.model.BaseResponse
import com.bgargarella.ram.data.character.model.CharacterResponse
import com.bgargarella.ram.data.episode.model.EpisodeResponse
import com.bgargarella.ram.data.location.model.LocationResponse
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
    ): Response<List<CharacterResponse>>

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int,
    ): Response<CharacterResponse>

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int,
    ): Response<BaseResponse<EpisodeResponse>>

    @GET("episode/{ids}")
    suspend fun getEpisodes(
        @Path("ids") ids: List<Int>,
    ): Response<List<EpisodeResponse>>

    @GET("episode/{id}")
    suspend fun getEpisode(
        @Path("id") id: Int,
    ): Response<EpisodeResponse>

    @GET("location")
    suspend fun getLocations(
        @Query("page") page: Int,
    ): Response<BaseResponse<LocationResponse>>

    @GET("location/{ids}")
    suspend fun getLocations(
        @Path("ids") ids: List<Int>,
    ): Response<List<LocationResponse>>

    @GET("location/{id}")
    suspend fun getLocation(
        @Path("id") id: Int,
    ): Response<LocationResponse>
}