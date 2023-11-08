package com.bgargarella.ram.domain.character.usecase

import androidx.paging.PagingData
import com.bgargarella.ram.domain.base.usecase.GetEntitiesByIdsUseCase
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import com.bgargarella.ram.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersByLocationUseCase(
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository,
) : GetEntitiesByIdsUseCase() {

    operator fun invoke(id: Int): Flow<PagingData<Character>> =
        invoke(
            getLocalModel = { locationRepository.getLocation(id) },
            getIds = { it.residents },
            getRemoteEntities = characterRepository::getCharacters,
        )
}