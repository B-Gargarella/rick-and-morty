package com.bgargarella.ram.domain.character.usecase

import com.bgargarella.ram.domain.base.usecase.GetEntitiesByIdsUseCase
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import com.bgargarella.ram.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersByLocationUseCase(
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository,
) : GetEntitiesByIdsUseCase() {

    suspend operator fun invoke(id: Int): Flow<List<Character>> =
        getEntitiesById(
            getEntity = { locationRepository.getLocation(id) },
            mappingAction = { it.residents },
            getEntities = characterRepository::getCharacters,
        )
}