package com.bgargarella.ram.data.api

import com.bgargarella.ram.data.BuildConfig
import com.bgargarella.ram.data.dto.PageResponseDTO
import com.bgargarella.ram.data.entity.PageResponse
import com.bgargarella.ram.data.util.toPageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter

open class BaseAPIService {

    protected val client = HttpClient(CIO)

    protected suspend inline fun <reified T> get(
        endpoint: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T = client.get(
        urlString = "${BuildConfig.BASE_URL}$endpoint",
        block = block
    ).body<T>()

    protected suspend inline fun <reified T, V> getEndpointPage(
        endpoint: String,
        page: Int,
        noinline action: (T) -> V
    ): PageResponse<V> =
        get<PageResponseDTO<T>>(endpoint = endpoint) {
            parameter("page", page)
        }.toPageResponse(
            page = page,
            action = action
        )

    protected suspend inline fun <reified T, V> getEndpointIds(
        endpoint: String,
        ids: List<Int>,
        action: (T) -> V
    ): List<V> =
        get<List<T>>(
            endpoint = "$endpoint/${ids.joinToString(separator = ",")}"
        ).map(transform = action)

    protected suspend inline fun <reified T, V> getEndpointId(
        endpoint: String,
        id: Int,
        action: (T) -> V
    ): V =
        action(get<T>(endpoint = "$endpoint/$id"))
}