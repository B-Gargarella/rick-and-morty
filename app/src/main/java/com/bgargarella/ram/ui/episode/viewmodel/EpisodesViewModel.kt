package com.bgargarella.ram.ui.episode.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.episode.usecase.GetEpisodesByCharacterUseCase
import com.bgargarella.ram.domain.episode.usecase.GetEpisodesUseCase
import com.bgargarella.ram.ui.base.model.UiState
import com.bgargarella.ram.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val getEpisodesByCharacterUseCase: GetEpisodesByCharacterUseCase,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState<Episode>> =
        MutableStateFlow(UiState())

    val uiState: StateFlow<UiState<Episode>> =
        _uiState.asStateFlow()

    fun getEpisodes(): Flow<PagingData<Episode>> =
        getEpisodesUseCase().cachedIn(viewModelScope)

    suspend fun getEpisodesByCharacter(id: Int) {
        getEpisodesByCharacterUseCase(id).getEntitiesById(_uiState)
    }
}