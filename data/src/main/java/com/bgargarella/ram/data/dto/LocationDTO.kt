package com.bgargarella.ram.data.dto

import com.bgargarella.ram.data.entity.LocationEntity
import com.bgargarella.ram.data.util.getIdFromCharacter
import com.bgargarella.ram.data.util.getValue

data class LocationDTO(
    override val id: Int,
    override val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>
): BaseDTO {
    override fun toModel(): LocationEntity =
        LocationEntity(
            id = id,
            name = name,
            type = type.getValue(),
            dimension = dimension.getValue(),
            residents = residents.map { it.getIdFromCharacter() }
        )
}