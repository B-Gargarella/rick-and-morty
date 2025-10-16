package com.bgargarella.ram.data.entity

data class PageResponse<T>(
    val hasNext: Boolean,
    val page: Int,
    val results: List<T>
)