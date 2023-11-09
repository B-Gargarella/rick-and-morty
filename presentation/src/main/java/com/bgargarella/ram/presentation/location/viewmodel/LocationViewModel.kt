package com.bgargarella.ram.presentation.location.viewmodel

import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.domain.location.usecase.GetLocationUseCase
import com.bgargarella.ram.presentation.base.viewmodel.BaseViewModel
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.model.Result.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<Result<Location>> = MutableStateFlow(Loading())

    val uiState: StateFlow<Result<Location>> = _uiState.asStateFlow()

    suspend fun getLocation(id: Int) {
        getLocationUseCase(id).invokeFlow(_uiState)
    }
}