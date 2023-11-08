package com.bgargarella.ram.domain.episode.model

data class Episode(
    val id: Int,
    val name: String,
    val releaseDate: String,
    val code: String,
    val characters: List<Int>,
)