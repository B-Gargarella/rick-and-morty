package com.bgargarella.ram.data.location.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationModel(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String?,
    val dimension: String?,
    val residents: List<Int>,
)