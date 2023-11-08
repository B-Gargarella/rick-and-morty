package com.bgargarella.ram.data.episode.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode")
data class EpisodeModel(
    @PrimaryKey val id: Int,
    val name: String,
    val releaseDate: String,
    val code: String,
    val characters: List<Int>,
)