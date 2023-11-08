package com.bgargarella.ram.data.episode.model

data class EpisodeResponse(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>
)