package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Location
import com.bgargarella.ram.domain.model.Result
import com.bgargarella.ram.domain.repository.InfoRepository

class GetLocationUseCase(
    private val repository: InfoRepository
) {

    suspend operator fun invoke(id: Int): Result<Location> =
        repository.getLocation(id = id)
}