package com.bgargarella.ram.domain.entities

data class Character(
    override val id: Int,
    override val name: String,
    val avatar: String,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: CharacterLocation?,
    val location: CharacterLocation?,
    val episodes: List<Int>
) : BaseModel