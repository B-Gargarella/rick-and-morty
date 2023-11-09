package com.bgargarella.ram.presentation.character.viewmodel

import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.usecase.GetCharacterUseCase
import com.bgargarella.ram.presentation.base.viewmodel.BaseViewModel
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.model.Result.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<Result<Character>> = MutableStateFlow(Loading())

    val uiState: StateFlow<Result<Character>> = _uiState.asStateFlow()

    suspend fun getCharacter(id: Int) {
        getCharacterUseCase(id).invokeFlow(_uiState)
    }
}