package com.bgargarella.ram.data.character.mapper

import com.bgargarella.ram.data.character.model.CharacterModel
import com.bgargarella.ram.data.character.model.CharacterResponse
import com.bgargarella.ram.data.character.model.CharacterResponse.CharacterLocationEntity
import com.bgargarella.ram.data.util.getIdFromEpisode
import com.bgargarella.ram.data.util.getIdFromLocation
import com.bgargarella.ram.data.util.getValue
import com.bgargarella.ram.domain.BuildConfig
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.model.CharacterLocationModel

fun CharacterResponse.toCharacterModel(): CharacterModel =
    CharacterModel(
        id = id,
        name = name,
        status = status.getValue(),
        species = species.getValue(),
        type = type.getValue(),
        gender = gender.getValue(),
        origin = origin.getCharacterLocation(),
        location = location.getCharacterLocation(),
        episodes = episode.map { it.getIdFromEpisode() },
    )

fun CharacterModel.toCharacter(): Character =
    Character(
        id = id,
        name = name,
        avatar = getAvatar(),
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin,
        location = location,
        episodes = episodes,
    )

fun Character.toCharacterModel(): CharacterModel =
    CharacterModel(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin,
        location = location,
        episodes = episodes,
    )

private fun CharacterModel.getAvatar(): String = "${BuildConfig.BASE_URL}character/avatar/$id.jpeg"

private fun CharacterLocationEntity.getCharacterLocation(): CharacterLocationModel? =
    if (name.getValue() == null || url.getValue() == null) {
        null
    } else {
        CharacterLocationModel(
            id = url.getIdFromLocation(),
            name = name,
        )
    }