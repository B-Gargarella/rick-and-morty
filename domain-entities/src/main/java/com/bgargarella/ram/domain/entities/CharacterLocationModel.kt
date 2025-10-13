package com.bgargarella.ram.domain.entities

import com.bgargarella.ram.domain.entities.BaseEntity

data class CharacterLocationModel(
    override val id: Int,
    override val name: String
) : BaseEntity