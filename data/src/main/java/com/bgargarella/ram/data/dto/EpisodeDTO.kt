package com.bgargarella.ram.data.dto

import com.bgargarella.ram.data.entity.EpisodeEntity
import com.bgargarella.ram.data.util.getIdFromCharacter
import kotlinx.serialization.SerialName

data class EpisodeDTO(
    override val id: Int,
    override val name: String,
    @SerialName(value = "air_date") val airDate: String,
    val episode: String,
    val characters: List<String>
): BaseDTO {
    override fun toModel(): EpisodeEntity =
        EpisodeEntity(
            id = id,
            name = name,
            releaseDate = airDate,
            code = episode,
            characters = characters.map { it.getIdFromCharacter() }
        )
}