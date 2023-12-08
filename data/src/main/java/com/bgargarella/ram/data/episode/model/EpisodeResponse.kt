package com.bgargarella.ram.data.episode.model

import com.bgargarella.ram.data.base.model.BaseResponse

data class EpisodeResponse(
    override val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>
): BaseResponse