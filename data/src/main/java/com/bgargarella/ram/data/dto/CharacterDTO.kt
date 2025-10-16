package com.bgargarella.ram.data.dto

import com.bgargarella.ram.data.entity.CharacterEntity
import com.bgargarella.ram.data.util.getIdFromEpisode
import com.bgargarella.ram.data.util.getIdFromLocation
import com.bgargarella.ram.data.util.getValue
import com.bgargarella.ram.domain.entities.CharacterLocation

data class CharacterDTO(
    override val id: Int,
    override val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharacterLocationDTO,
    val location: CharacterLocationDTO,
    val episode: List<String>
) : BaseDTO {
    override fun toModel(): CharacterEntity =
        CharacterEntity(
            id = id,
            name = name,
            status = status.getValue(),
            species = species.getValue(),
            type = type.getValue(),
            gender = gender.getValue(),
            origin = origin.getCharacterLocation(),
            location = location.getCharacterLocation(),
            episodes = episode.map { it.getIdFromEpisode() }
        )

    private fun CharacterLocationDTO.getCharacterLocation(): CharacterLocation? =
        if (name.getValue() == null || url.getValue() == null) {
            null
        } else {
            CharacterLocation(
                id = url.getIdFromLocation(),
                name = name
            )
        }
}