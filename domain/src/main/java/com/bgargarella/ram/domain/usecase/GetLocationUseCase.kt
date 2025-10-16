package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Location
import com.bgargarella.ram.domain.repository.BaseEntityRepository
import kotlinx.coroutines.flow.Flow

class GetLocationUseCase(
    private val repository: BaseEntityRepository<Location>
) {

    suspend operator fun invoke(id: Int): Flow<Location> =
        repository.getEntity(id = id)
}