package com.bgargarella.ram.data.repository

import com.bgargarella.ram.data.entity.PageResponse
import com.bgargarella.ram.domain.entities.BaseEntity
import com.bgargarella.ram.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

open class BaseRepository {
    suspend inline fun <T, V> handleDataSources(
        crossinline getRemote: suspend () -> T,
        crossinline saveLocal: suspend (V) -> Unit,
        crossinline toModel: T.() -> V,
        crossinline getLocal: suspend () -> V
    ): Flow<Result<V>> =
        flow {
            try {
                val remoteData: T = getRemote()
                val remoteModel: V = remoteData.toModel()
                saveLocal(remoteModel)
            } catch (e: Exception) {
                emit(Result.Unknown<V>(message = e.message.toString()))
            }
            emit(Result.Success<V>(data = getLocal()))
        }
    // 1. Synchronize the data (Ktor -> Room) and return the local result
    suspend inline fun <T : BaseEntity, V> getEntity(
        getRemote: suspend () -> T,
        saveLocal: suspend (T) -> Unit,
        getLocal: suspend () -> T,
        toModel: T.() -> V
    ): Flow<V> {
        // Run the entire logic within a single suspend function
        try {
            // Fetch from Ktor
            val entity: T = getRemote()
            // Save/Update Room
            saveLocal(entity)
        } catch (e: Exception) {
            // Handle network failure, but continue to return local data
            println("Network sync failed: ${e.message}")
        }
        // Always return the data from the single source of truth (Room)
        return flowOf(getLocal().toModel())
    }

    // 1. Synchronize the data (Ktor -> Room) and return the local result
    suspend inline fun <T : BaseEntity, V> getPagedEntities(
        getRemote: suspend () -> PageResponse<T>,
        saveLocal: suspend (List<T>) -> Unit,
        getLocal: suspend (List<Int>) -> List<T>,
        toModel: T.() -> V
    ): Flow<List<V>> {
        // Run the entire logic within a single suspend function
        var ids: List<Int> = emptyList()
        try {
            // Fetch from Ktor
            val entities: PageResponse<T> = getRemote()
            // Save/Update Room
            val results: List<T> = entities.results
            saveLocal(results)
            ids = results.map { it.id }
        } catch (e: Exception) {
            // Handle network failure, but continue to return local data
            println("Network sync failed: ${e.message}")
        }
        // Always return the data from the single source of truth (Room)
        return flowOf(getLocal(ids).map { it.toModel() })
    }

    // 1. Synchronize the data (Ktor -> Room) and return the local result
    suspend inline fun <T : BaseEntity, V> getEntities(
        getRemote: suspend () -> List<T>,
        saveLocal: suspend (List<T>) -> Unit,
        getLocal: suspend () -> List<T>,
        toModel: T.() -> V
    ): Flow<List<V>> {
        // Run the entire logic within a single suspend function
        try {
            // Fetch from Ktor
            val entities: List<T> = getRemote()
            // Save/Update Room
            saveLocal(entities)
        } catch (e: Exception) {
            // Handle network failure, but continue to return local data
            println("Network sync failed: ${e.message}")
        }
        // Always return the data from the single source of truth (Room)
        return flowOf(getLocal().map { it.toModel() })
    }

    // 1. Synchronize the data (Ktor -> Room) and return the local result
    suspend inline fun <T : BaseEntity, V, W> getEntityAttributes(
        getRemote: suspend () -> T,
        saveLocalEntity: suspend (T) -> Unit,
        toAttributeIds: T.() -> List<Int>,
        getRemoteAttributes: suspend (List<Int>) -> List<V>,
        saveLocalAttributes: suspend (List<V>) -> Unit,
        getLocalAttributes: suspend (List<Int>) -> List<V>,
        toAttribute: V.() -> W
    ): Flow<List<W>> {
        // Run the entire logic within a single suspend function
        var ids: List<Int> = emptyList()
        var localAttributes: List<V> = emptyList()
        try {
            // Fetch from Ktor
            val entity: T = getRemote()
            // Save/Update Room
            saveLocalEntity(entity)
            ids = entity.toAttributeIds()

            // Fetch from Ktor
            val remoteAttributes: List<V> = getRemoteAttributes(ids)
            // Save/Update Room
            saveLocalAttributes(remoteAttributes)

            localAttributes = getLocalAttributes(ids)
        } catch (e: Exception) {
            // Handle network failure, but continue to return local data
            println("Network sync failed: ${e.message}")
        }
        // Always return the data from the single source of truth (Room)
        return flowOf(localAttributes.map { it.toAttribute() })
    }
}