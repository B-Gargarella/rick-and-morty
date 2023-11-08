package com.bgargarella.ram.ui.character.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.usecase.GetCharactersByEpisodeUseCase
import com.bgargarella.ram.domain.character.usecase.GetCharactersByLocationUseCase
import com.bgargarella.ram.domain.character.usecase.GetCharactersUseCase
import com.bgargarella.ram.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharactersByEpisodeUseCase: GetCharactersByEpisodeUseCase,
    private val getCharactersByLocationUseCase: GetCharactersByLocationUseCase,
) : BaseViewModel() {

    fun getCharacters(): Flow<PagingData<Character>> =
        getCharactersUseCase().cachedIn(viewModelScope)

    fun getCharacterTest(id: Int): Flow<PagingData<Character>> =
        getCharactersByEpisodeUseCase(id).cachedIn(viewModelScope)

    fun getCharactersByEpisode(id: Int): Flow<PagingData<Character>> =
        getCharactersByEpisodeUseCase(id).cachedIn(viewModelScope)

    fun getCharactersByLocation(id: Int): Flow<PagingData<Character>> =
        getCharactersByLocationUseCase(id).cachedIn(viewModelScope)
}