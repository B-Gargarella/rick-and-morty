package com.bgargarella.ram.domain.entities

import com.bgargarella.ram.domain.entities.BaseEntity

data class Location(
    override val id: Int,
    override val name: String,
    val type: String?,
    val dimension: String?,
    val residents: List<Int>
) : BaseEntity