package com.bgargarella.ram.presentation.episode.viewmodel

import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.episode.usecase.GetEpisodeUseCase
import com.bgargarella.ram.presentation.base.viewmodel.BaseViewModel
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.model.Result.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val getEpisodeUseCase: GetEpisodeUseCase,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<Result<Episode>> = MutableStateFlow(Loading())

    val uiState: StateFlow<Result<Episode>> = _uiState.asStateFlow()

    suspend fun getEpisode(id: Int) {
        getEpisodeUseCase(id).invokeFlow(_uiState)
    }
}