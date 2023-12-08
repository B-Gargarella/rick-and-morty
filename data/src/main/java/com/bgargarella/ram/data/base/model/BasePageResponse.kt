package com.bgargarella.ram.data.base.model

data class BasePageResponse<T>(val info: Info, val results: List<T>) {

    data class Info(
        val count: Int,
        val pages: Int,
        val next: String? = null,
        val prev: String? = null
    )
}