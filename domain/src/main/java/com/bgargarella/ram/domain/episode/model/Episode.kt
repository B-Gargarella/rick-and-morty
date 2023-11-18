package com.bgargarella.ram.domain.episode.model

import com.bgargarella.ram.domain.base.model.BaseEntity

data class Episode(
    override val id: Int,
    val name: String,
    val releaseDate: String,
    val code: String,
    val characters: List<Int>,
) : BaseEntity