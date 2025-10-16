package com.bgargarella.ram.domain.entities

interface BaseEntity {
    val id: Int
    val name: String

    fun toDomain(): BaseModel
}