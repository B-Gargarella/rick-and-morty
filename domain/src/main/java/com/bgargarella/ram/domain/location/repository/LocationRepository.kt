package com.bgargarella.ram.domain.location.repository

import androidx.paging.PagingData
import com.bgargarella.ram.domain.location.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getLocation(id: Int): Flow<Location>

    fun getLocations(): Flow<PagingData<Location>>

    suspend fun getLocations(ids: List<Int>): List<Location>
}