package com.bgargarella.ram.data.entity.episode.mapper

import com.bgargarella.ram.data.entity.episode.model.EpisodeModel
import com.bgargarella.ram.data.entity.episode.model.EpisodeResponse
import com.bgargarella.ram.data.util.getIdFromCharacter
import com.bgargarella.ram.domain.episode.model.Episode

fun EpisodeResponse.toModel(): EpisodeModel =
    EpisodeModel(
        id = id,
        name = name,
        releaseDate = air_date,
        code = episode,
        characters = characters.map { it.getIdFromCharacter() }
    )

fun EpisodeModel.toEntity(): Episode =
    Episode(
        id = id,
        name = name,
        releaseDate = releaseDate,
        code = code,
        characters = characters
    )