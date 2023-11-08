package com.bgargarella.ram.ui.episode.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.episode.usecase.GetEpisodesByCharacterUseCase
import com.bgargarella.ram.domain.episode.usecase.GetEpisodesUseCase
import com.bgargarella.ram.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val getEpisodesByCharacterUseCase: GetEpisodesByCharacterUseCase,
) : BaseViewModel() {

    private val _episodesState: MutableStateFlow<EpisodesState> =
        MutableStateFlow(EpisodesState())

    val episodesState: StateFlow<EpisodesState> =
        _episodesState.asStateFlow()

    private val _episodesState2: MutableStateFlow<EpisodesState2> =
        MutableStateFlow(EpisodesState2())

    val episodesState2: StateFlow<EpisodesState2> =
        _episodesState2.asStateFlow()

    fun getEpisodes(): Flow<PagingData<Episode>> =
        getEpisodesUseCase().cachedIn(viewModelScope)

    fun getEpisodesByCharacter(id: Int): Flow<PagingData<Episode>> =
        getEpisodesByCharacterUseCase.invoke(id).cachedIn(viewModelScope)

    fun getEpisodes(ids: List<Int>): Flow<PagingData<Episode>> =
        getEpisodesByCharacterUseCase.invoke3(ids).cachedIn(viewModelScope)

    fun getEpisodes3(id: Int): Flow<Flow<PagingData<Episode>>> =
        getEpisodesByCharacterUseCase.invoke4(id)

    fun setEpisodesFromCharacter(ids: List<Int>) {
        _episodesState.update { prevState ->
            prevState.copy(
                isLoading = false,
                pagingData = getEpisodesByCharacterUseCase.invoke3(ids)
            )
        }
        /*
        _characterState.update { prevState ->
            prevState.copy(
                isLoading = false,
                character = null,
            )
        }
        */
    }

    suspend fun getEpisodesTest(id: Int) {
        getEpisodesByCharacterUseCase
            .getCharacterTest(id)
            .collect {
                _episodesState.update { prevState ->
                    prevState.copy(pagingData = getEpisodes(it?.episodes.orEmpty()))
                }
            }
    }

    suspend fun getListTest(id: Int) {
        getEpisodesByCharacterUseCase
            .getCharacter(id)
            .collect { entity ->
                _episodesState2.update { prevState ->
                    prevState.copy(isLoading = true)
                }
                _episodesState2.update { prevState ->
                    prevState.copy(
                        isLoading = false,
                        content = getEpisodesByCharacterUseCase.invoke5(entity?.episodes.orEmpty()),
                    )
                }
            }
    }
}

data class CharacterState(
    val isLoading: Boolean = false,
    val character: Character? = null,
)

data class EpisodesState(
    val isLoading: Boolean = false,
    val pagingData: Flow<PagingData<Episode>> = emptyFlow(),
)

data class EpisodesState2(
    val isLoading: Boolean = false,
    val content: List<Episode> = emptyList(),
)