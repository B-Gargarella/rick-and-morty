package com.bgargarella.ram.data.episode.mapper

import com.bgargarella.ram.data.episode.model.EpisodeModel
import com.bgargarella.ram.data.episode.model.EpisodeResponse
import com.bgargarella.ram.data.util.getIdFromCharacter
import com.bgargarella.ram.domain.episode.model.Episode

fun EpisodeResponse.toEpisodeModel(): EpisodeModel =
    EpisodeModel(
        id = id,
        name = name,
        releaseDate = air_date,
        code = episode,
        characters = characters.map { it.getIdFromCharacter() },
    )

fun EpisodeModel.toEpisode(): Episode =
    Episode(
        id = id,
        name = name,
        releaseDate = releaseDate,
        code = code,
        characters = characters,
    )

fun Episode.toEpisodeModel(): EpisodeModel =
    EpisodeModel(
        id = id,
        name = name,
        releaseDate = releaseDate,
        code = code,
        characters = characters,
    )