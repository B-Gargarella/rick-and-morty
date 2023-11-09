package com.bgargarella.ram.domain.character.usecase

import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.usecase.GetEntitiesByIdsUseCase
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersByEpisodeUseCase(
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository,
) : GetEntitiesByIdsUseCase() {

    suspend operator fun invoke(id: Int): Flow<Result<List<Character>>> =
        getEntitiesById(
            getEntity = { episodeRepository.getEpisode(id) },
            mappingAction = { it.characters },
            getEntities = characterRepository::getCharacters,
        )
}