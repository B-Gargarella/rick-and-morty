package com.bgargarella.ram.domain.character.model

data class Character(
    val id: Int,
    val name: String,
    val avatar: String,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: CharacterLocationModel?,
    val location: CharacterLocationModel?,
    val episodes: List<Int>,
)