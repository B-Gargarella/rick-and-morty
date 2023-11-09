package com.bgargarella.ram.domain.episode.usecase

import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.usecase.GetEntitiesByIdsUseCase
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodesByCharacterUseCase(
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository,
) : GetEntitiesByIdsUseCase() {

    suspend operator fun invoke(id: Int): Flow<Result<List<Episode>>> =
        getEntitiesById(
            getEntity = { characterRepository.getCharacter(id) },
            mappingAction = { it.episodes },
            getEntities = episodeRepository::getEpisodes,
        )
}