package com.bgargarella.ram.data.episode.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bgargarella.ram.data.base.model.BaseModel

@Entity(tableName = "episode")
data class EpisodeModel(
    @PrimaryKey override val id: Int,
    val name: String,
    val releaseDate: String,
    val code: String,
    val characters: List<Int>,
) : BaseModel