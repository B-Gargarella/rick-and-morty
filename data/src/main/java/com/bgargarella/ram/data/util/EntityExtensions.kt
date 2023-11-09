package com.bgargarella.ram.data.util

import com.bgargarella.ram.domain.BuildConfig

fun String.getIdFromCharacter(): Int = getIdFromUrl(this, "character")

fun String.getIdFromEpisode(): Int = getIdFromUrl(this, "episode")

fun String.getIdFromLocation(): Int = getIdFromUrl(this, "location")

private fun getIdFromUrl(url: String, prefix: String): Int =
    url.removePrefix("${BuildConfig.BASE_URL}$prefix/").toInt()

fun String?.getValue(): String? =
    if (this == "unknown" || isNullOrBlank()) {
        null
    } else {
        this
    }