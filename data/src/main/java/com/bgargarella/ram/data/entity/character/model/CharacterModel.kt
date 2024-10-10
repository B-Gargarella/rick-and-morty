package com.bgargarella.ram.data.entity.character.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bgargarella.ram.data.entity.base.model.BaseModel
import com.bgargarella.ram.data.util.CHARACTER
import com.bgargarella.ram.domain.character.model.CharacterLocationModel

@Entity(tableName = CHARACTER)
data class CharacterModel(
    @PrimaryKey override val id: Int,
    override val name: String,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: CharacterLocationModel?,
    val location: CharacterLocationModel?,
    val episodes: List<Int>,
) : BaseModel