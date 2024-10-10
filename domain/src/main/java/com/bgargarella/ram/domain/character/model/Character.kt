package com.bgargarella.ram.domain.character.model

import com.bgargarella.ram.domain.base.model.BaseEntity

data class Character(
    override val id: Int,
    override val name: String,
    val avatar: String,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: CharacterLocationModel?,
    val location: CharacterLocationModel?,
    val episodes: List<Int>
) : BaseEntity