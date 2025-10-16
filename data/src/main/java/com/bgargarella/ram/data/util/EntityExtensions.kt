package com.bgargarella.ram.data.util

import com.bgargarella.ram.data.BuildConfig
import com.bgargarella.ram.data.dto.PageResponseDTO
import com.bgargarella.ram.data.entity.PageResponse

const val CHARACTER: String = "character"
const val EPISODE: String = "episode"
const val LOCATION: String = "location"

fun String.getIdFromCharacter(): Int = getIdFromUrl(this, CHARACTER)

fun String.getIdFromEpisode(): Int = getIdFromUrl(this, EPISODE)

fun String.getIdFromLocation(): Int = getIdFromUrl(this, LOCATION)

private fun getIdFromUrl(url: String, prefix: String): Int =
    url.removePrefix("${BuildConfig.BASE_URL}$prefix/").toDouble().toInt()

fun String?.getValue(): String? =
    if (this == "unknown" || isNullOrBlank()) {
        null
    } else {
        this
    }

fun <T, V> PageResponseDTO<T>.toPageResponse(
    page: Int,
    action: (T) -> V
): PageResponse<V> =
    PageResponse(
        hasNext = info.next != null,
        page = page,
        results = results.map(transform = action)
    )