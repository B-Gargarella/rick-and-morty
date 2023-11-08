package com.bgargarella.ram.domain.location.model

data class Location(
    val id: Int,
    val name: String,
    val type: String?,
    val dimension: String?,
    val residents: List<Int>,
)