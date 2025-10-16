package com.bgargarella.ram.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bgargarella.ram.data.BuildConfig
import com.bgargarella.ram.data.util.CHARACTER
import com.bgargarella.ram.domain.entities.BaseEntity
import com.bgargarella.ram.domain.entities.Character
import com.bgargarella.ram.domain.entities.CharacterLocation

@Entity(tableName = CHARACTER)
data class CharacterEntity(
    @PrimaryKey override val id: Int,
    override val name: String,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: CharacterLocation?,
    val location: CharacterLocation?,
    val episodes: List<Int>
) : BaseEntity {
    override fun toDomain(): Character =
        Character(
            id = id,
            name = name,
            avatar = getAvatar(),
            status = status,
            species = species,
            type = type,
            gender = gender,
            origin = origin,
            location = location,
            episodes = episodes
        )

    private fun CharacterEntity.getAvatar(): String = "${BuildConfig.BASE_URL}character/avatar/$id.jpeg"
}