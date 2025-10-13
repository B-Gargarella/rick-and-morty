package com.bgargarella.ram.domain.entities

import com.bgargarella.ram.domain.entities.BaseEntity

data class Episode(
    override val id: Int,
    override val name: String,
    val releaseDate: String,
    val code: String,
    val characters: List<Int>
) : BaseEntity