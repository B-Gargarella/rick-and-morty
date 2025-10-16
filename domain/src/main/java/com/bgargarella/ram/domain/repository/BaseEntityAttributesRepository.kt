package com.bgargarella.ram.domain.repository

import kotlinx.coroutines.flow.Flow

interface BaseEntityAttributesRepository<T> {

    suspend fun getEntityAttributes(id: Int): Flow<List<T>>
}