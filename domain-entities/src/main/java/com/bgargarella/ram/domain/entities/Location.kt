package com.bgargarella.ram.domain.entities

data class Location(
    override val id: Int,
    override val name: String,
    val type: String?,
    val dimension: String?,
    val residents: List<Int>
) : BaseModel