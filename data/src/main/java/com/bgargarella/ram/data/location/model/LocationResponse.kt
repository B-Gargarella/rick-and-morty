package com.bgargarella.ram.data.location.model

import com.bgargarella.ram.data.base.model.BaseResponse

data class LocationResponse(
    override val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
): BaseResponse