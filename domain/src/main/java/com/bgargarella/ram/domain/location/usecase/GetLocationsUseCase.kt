package com.bgargarella.ram.domain.location.usecase

import androidx.paging.PagingData
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocationsUseCase(private val repository: LocationRepository) {

    operator fun invoke(): Flow<PagingData<Location>> = repository.getLocations()
}