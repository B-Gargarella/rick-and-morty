package com.bgargarella.ram.data.entity.location.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bgargarella.ram.data.entity.base.model.BaseModel
import com.bgargarella.ram.data.util.LOCATION

@Entity(tableName = LOCATION)
data class LocationModel(
    @PrimaryKey override val id: Int,
    override val name: String,
    val type: String?,
    val dimension: String?,
    val residents: List<Int>,
) : BaseModel