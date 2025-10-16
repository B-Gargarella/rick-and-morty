package com.bgargarella.ram.domain.repository

import kotlinx.coroutines.flow.Flow

interface BaseEntityRepository<T> {

    suspend fun getEntity(id: Int): Flow<T>

    suspend fun getEntitiesPage(page: Int): Flow<List<T>>

    suspend fun getEntities(ids: List<Int>): Flow<List<T>>
}