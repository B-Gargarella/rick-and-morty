package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Location
import com.bgargarella.ram.domain.model.Result
import com.bgargarella.ram.domain.repository.InfoRepository

class GetLocationsUseCase(
    private val repository: InfoRepository
) {

    suspend operator fun invoke(ids: List<Int>): Result<List<Location>> =
        repository.getLocations(ids = ids)
}