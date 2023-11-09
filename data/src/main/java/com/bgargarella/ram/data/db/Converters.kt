package com.bgargarella.ram.data.db

import androidx.room.TypeConverter
import com.bgargarella.ram.data.character.model.CharacterModel
import com.bgargarella.ram.data.character.model.CharacterResponse
import com.bgargarella.ram.data.character.model.CharacterResponse.CharacterLocationEntity
import com.bgargarella.ram.data.episode.model.EpisodeModel
import com.bgargarella.ram.data.episode.model.EpisodeResponse
import com.bgargarella.ram.data.location.model.LocationModel
import com.bgargarella.ram.data.location.model.LocationResponse
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.model.CharacterLocationModel
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.location.model.Location
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Singleton

class Converters {

    @Singleton
    private val moshi: Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private inline fun <reified T> getAdapter(): JsonAdapter<T> =
        moshi.adapter(T::class.java)

    private inline fun <reified T> jsonToObject(value: String): T? =
        getAdapter<T>().fromJson(value)

    private inline fun <reified T> objectToJson(value: T): String =
        getAdapter<T>().toJson(value)

    @TypeConverter
    fun jsonToCharacter(value: String?): Character? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun characterToJson(value: Character?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToCharacterModel(value: String?): CharacterModel? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun characterModelToJson(value: CharacterModel?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToCharacterResponse(value: String?): CharacterResponse? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun characterResponseToJson(value: CharacterResponse?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToEpisode(value: String?): Episode? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun episodeToJson(value: Episode?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToEpisodeModel(value: String?): EpisodeModel? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun episodeModelToJson(value: EpisodeModel?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToEpisodeResponse(value: String?): EpisodeResponse? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun episodeResponseToJson(value: EpisodeResponse?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToLocation(value: String?): Location? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun locationToJson(value: Location?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToLocationModel(value: String?): LocationModel? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun locationModelToJson(value: LocationModel?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToLocationResponse(value: String?): LocationResponse? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun locationResponseToJson(value: LocationResponse?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToCharacterLocationModel(value: String?): CharacterLocationModel? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun characterLocationModelToJson(value: CharacterLocationModel?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToCharacterLocationEntity(value: String?): CharacterLocationEntity? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun characterLocationEntityToJson(value: CharacterLocationEntity?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToListIds(value: String?): List<Int>? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun listIdsToJson(value: List<Int>?): String? =
        value?.let(::objectToJson)

    @TypeConverter
    fun jsonToEntityList(value: String?): List<String>? =
        value?.let(::jsonToObject)

    @TypeConverter
    fun listToEntityJson(value: List<String>?): String? =
        value?.let(::objectToJson)
}