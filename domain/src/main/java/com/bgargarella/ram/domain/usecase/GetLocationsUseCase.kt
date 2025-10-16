package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Location
import com.bgargarella.ram.domain.repository.BaseEntityRepository
import kotlinx.coroutines.flow.Flow

class GetLocationsUseCase(
    private val repository: BaseEntityRepository<Location>
) {

    suspend operator fun invoke(ids: List<Int>): Flow<List<Location>> =
        repository.getEntities(ids = ids)
}