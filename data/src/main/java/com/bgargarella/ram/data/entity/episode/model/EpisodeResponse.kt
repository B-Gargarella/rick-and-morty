package com.bgargarella.ram.data.entity.episode.model

import com.bgargarella.ram.data.entity.base.model.BaseResponse

data class EpisodeResponse(
    override val id: Int,
    override val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>
): BaseResponse