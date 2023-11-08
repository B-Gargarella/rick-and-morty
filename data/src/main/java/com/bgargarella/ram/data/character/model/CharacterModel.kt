package com.bgargarella.ram.data.character.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bgargarella.ram.domain.character.model.CharacterLocationModel

@Entity(tableName = "character")
data class CharacterModel(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: CharacterLocationModel?,
    val location: CharacterLocationModel?,
    val episodes: List<Int>,
)