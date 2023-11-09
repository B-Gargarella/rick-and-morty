package com.bgargarella.ram.presentation.character.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.usecase.GetCharactersByEpisodeUseCase
import com.bgargarella.ram.domain.character.usecase.GetCharactersByLocationUseCase
import com.bgargarella.ram.domain.character.usecase.GetCharactersUseCase
import com.bgargarella.ram.presentation.base.model.UiState
import com.bgargarella.ram.presentation.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharactersByEpisodeUseCase: GetCharactersByEpisodeUseCase,
    private val getCharactersByLocationUseCase: GetCharactersByLocationUseCase,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState<Character>> =
        MutableStateFlow(UiState())

    val uiState: StateFlow<UiState<Character>> =
        _uiState.asStateFlow()

    fun getCharacters(): Flow<PagingData<Character>> =
        getCharactersUseCase().cachedIn(viewModelScope)

    suspend fun getCharactersByEpisode(id: Int) {
        getCharactersByEpisodeUseCase(id).getEntitiesById(_uiState)
    }

    suspend fun getCharactersByLocation(id: Int) {
        getCharactersByLocationUseCase(id).getEntitiesById(_uiState)
    }
}