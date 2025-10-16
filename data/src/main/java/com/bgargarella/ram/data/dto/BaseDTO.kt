package com.bgargarella.ram.data.dto

import com.bgargarella.ram.domain.entities.BaseEntity
import com.bgargarella.ram.domain.entities.BaseModel

interface BaseDTO: BaseModel {
    override val id: Int
    override val name: String

    fun toModel(): BaseEntity
}