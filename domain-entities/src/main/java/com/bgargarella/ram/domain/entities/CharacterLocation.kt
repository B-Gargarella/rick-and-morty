package com.bgargarella.ram.domain.entities

data class CharacterLocation(
    override val id: Int,
    override val name: String
) : BaseModel