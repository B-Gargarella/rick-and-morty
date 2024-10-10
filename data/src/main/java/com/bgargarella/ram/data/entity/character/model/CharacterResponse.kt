package com.bgargarella.ram.data.entity.character.model

import com.bgargarella.ram.data.entity.base.model.BaseResponse

data class CharacterResponse(
    override val id: Int,
    override val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharacterLocationEntity,
    val location: CharacterLocationEntity,
    val episode: List<String>
): BaseResponse {
    data class CharacterLocationEntity(val name: String, val url: String)
}