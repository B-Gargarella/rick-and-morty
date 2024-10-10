package com.bgargarella.ram.data.entity.base.model

data class BasePageResponse<T>(val info: Info, val results: List<T>) {

    data class Info(
        val count: Int,
        val pages: Int,
        val next: String? = null,
        val prev: String? = null
    )
}