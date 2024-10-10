package com.bgargarella.ram.data.entity.episode.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bgargarella.ram.data.entity.base.model.BaseModel
import com.bgargarella.ram.data.util.EPISODE

@Entity(tableName = EPISODE)
data class EpisodeModel(
    @PrimaryKey override val id: Int,
    override val name: String,
    val releaseDate: String,
    val code: String,
    val characters: List<Int>,
) : BaseModel