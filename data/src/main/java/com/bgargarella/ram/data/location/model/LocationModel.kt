package com.bgargarella.ram.data.location.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bgargarella.ram.data.base.model.BaseModel

@Entity(tableName = "location")
data class LocationModel(
    @PrimaryKey override val id: Int,
    val name: String,
    val type: String?,
    val dimension: String?,
    val residents: List<Int>,
) : BaseModel