package com.bgargarella.ram.domain.location.model

import com.bgargarella.ram.domain.base.model.BaseEntity

data class Location(
    override val id: Int,
    val name: String,
    val type: String?,
    val dimension: String?,
    val residents: List<Int>,
) : BaseEntity