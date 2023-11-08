package com.bgargarella.ram.ui.location.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.domain.location.usecase.GetLocationsUseCase
import com.bgargarella.ram.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
) : BaseViewModel() {

    suspend fun getLocations(): Flow<PagingData<Location>> =
        getLocationsUseCase().cachedIn(viewModelScope)
}