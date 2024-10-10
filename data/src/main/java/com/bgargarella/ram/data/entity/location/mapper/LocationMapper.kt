package com.bgargarella.ram.data.entity.location.mapper

import com.bgargarella.ram.data.entity.location.model.LocationModel
import com.bgargarella.ram.data.entity.location.model.LocationResponse
import com.bgargarella.ram.data.util.getIdFromCharacter
import com.bgargarella.ram.data.util.getValue
import com.bgargarella.ram.domain.location.model.Location

fun LocationResponse.toModel(): LocationModel =
    LocationModel(
        id = id,
        name = name,
        type = type.getValue(),
        dimension = dimension.getValue(),
        residents = residents.map { it.getIdFromCharacter() },
    )

fun LocationModel.toEntity(): Location =
    Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents,
    )