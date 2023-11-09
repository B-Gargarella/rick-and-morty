package com.bgargarella.ram.presentation.base.viewmodel

import androidx.lifecycle.ViewModel
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.model.Result.Success
import com.bgargarella.ram.domain.base.model.Result.Unknown
import com.bgargarella.ram.presentation.base.model.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update

open class BaseViewModel : ViewModel() {

    protected suspend fun <T> Flow<Result<List<T>>>.getEntitiesById(
        uiState: MutableStateFlow<UiState<T>>,
    ) {
        cancellable()
            .catch {
                uiState.update { prevState ->
                    prevState.copy(
                        isLoading = false,
                        content = emptyList(),
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .collect { result: Result<List<T>> ->
                uiState.update { prevState ->
                    prevState.copy(isLoading = true)
                }
                uiState.update { prevState ->
                    prevState.copy(
                        isLoading = false,
                        content = (result as? Success)?.data.orEmpty(),
                    )
                }
            }
    }

    protected suspend fun <T> Flow<Result<T>>.invokeFlow(
        uiState: MutableStateFlow<Result<T>>,
    ) {
        cancellable()
            .catch {
                uiState.value = Unknown(message = it.message.toString())
            }
            .flowOn(Dispatchers.IO)
            .collect { result: Result<T> ->
                uiState.value = result
            }
    }
}