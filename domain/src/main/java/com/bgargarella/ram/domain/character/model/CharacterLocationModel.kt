package com.bgargarella.ram.domain.character.model

import com.bgargarella.ram.domain.base.model.BaseEntity

data class CharacterLocationModel(
    override val id: Int,
    override val name: String
) : BaseEntity