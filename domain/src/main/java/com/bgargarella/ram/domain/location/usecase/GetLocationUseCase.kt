package com.bgargarella.ram.domain.location.usecase

import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocationUseCase(private val repository: LocationRepository) {

    operator fun invoke(id: Int): Flow<Location> = repository.getLocation(id)
}