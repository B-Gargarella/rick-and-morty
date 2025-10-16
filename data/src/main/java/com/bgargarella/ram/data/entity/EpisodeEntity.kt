package com.bgargarella.ram.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bgargarella.ram.data.util.EPISODE
import com.bgargarella.ram.domain.entities.BaseEntity
import com.bgargarella.ram.domain.entities.Episode

@Entity(tableName = EPISODE)
data class EpisodeEntity(
    @PrimaryKey override val id: Int,
    override val name: String,
    val releaseDate: String,
    val code: String,
    val characters: List<Int>
) : BaseEntity {
    override fun toDomain(): Episode =
        Episode(
            id = id,
            name = name,
            releaseDate = releaseDate,
            code = code,
            characters = characters
        )
}