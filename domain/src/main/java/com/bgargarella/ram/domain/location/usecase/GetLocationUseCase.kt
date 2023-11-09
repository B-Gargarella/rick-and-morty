package com.bgargarella.ram.domain.location.usecase

import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocationUseCase(private val repository: LocationRepository) {

    operator fun invoke(id: Int): Flow<Result<Location>> = repository.getLocation(id)
}