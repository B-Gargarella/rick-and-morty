package com.bgargarella.ram.domain.character.usecase

import androidx.paging.PagingData
import com.bgargarella.ram.domain.base.usecase.GetEntitiesByIdsUseCase
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersByEpisodeUseCase(
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository,
) : GetEntitiesByIdsUseCase() {

    operator fun invoke(id: Int): Flow<PagingData<Character>> =
        invoke(
            getLocalModel = { episodeRepository.getEpisode(id) },
            getIds = { it.characters },
            getRemoteEntities = characterRepository::getCharacters,
        )
}