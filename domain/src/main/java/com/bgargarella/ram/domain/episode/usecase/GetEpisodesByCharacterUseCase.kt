package com.bgargarella.ram.domain.episode.usecase

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.paging.PagingData
import com.bgargarella.ram.domain.base.usecase.GetEntitiesByIdsUseCase
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class GetEpisodesByCharacterUseCase(
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository,
) : GetEntitiesByIdsUseCase() {

    fun getCharacter(id: Int): Flow<Character> =
        characterRepository.getCharacter(id)

    fun getCharacterTest(id: Int): Flow<Character?> =
        characterRepository.getCharacterTest(id)

    operator fun invoke(id: Int): Flow<PagingData<Episode>> =
        invoke(
            getLocalModel = { characterRepository.getCharacterTest(id) },
            getIds = { it?.episodes.orEmpty() },
            getRemoteEntities = episodeRepository::getEpisodes,
        )

    fun invoke2(id: Int): Flow<PagingData<Episode>> =
        characterRepository.getCharacter(id)
            .transform { entity ->
                Log.e("entity", entity.toString())
                emit(entity.episodes)
            }
            .transform { ids ->
                Log.e("ids", ids.toString())
                val episodes: Flow<PagingData<Episode>> = episodeRepository.getEpisodes(ids)
                Log.e("episodes", episodes.toString())
                val result: PagingData<Episode> = episodes.first()
                Log.e("result", result.toString())
            }

    fun invoke4(id: Int): Flow<Flow<PagingData<Episode>>> =
        characterRepository.getCharacterTest(id)
            .map { entity: Character? ->
                episodeRepository.getEpisodes(entity?.episodes.orEmpty())
            }
            /*
            .transform {
                emit(it.last())
            }
            */

    fun invoke3(ids: List<Int>): Flow<PagingData<Episode>> =
        episodeRepository.getEpisodes(ids)

    suspend fun invoke5(ids: List<Int>): List<Episode> =
        episodeRepository.getEpisodesTest(ids)
}