package com.bgargarella.ram.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bgargarella.ram.data.util.LOCATION
import com.bgargarella.ram.domain.entities.BaseEntity
import com.bgargarella.ram.domain.entities.Location

@Entity(tableName = LOCATION)
data class LocationEntity(
    @PrimaryKey override val id: Int,
    override val name: String,
    val type: String?,
    val dimension: String?,
    val residents: List<Int>
) : BaseEntity {
    override fun toDomain(): Location =
        Location(
            id = id,
            name = name,
            type = type,
            dimension = dimension,
            residents = residents
        )
}