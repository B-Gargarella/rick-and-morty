package com.bgargarella.ram.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import com.bgargarella.ram.ui.base.model.UiState
import com.bgargarella.ram.ui.util.Result
import com.bgargarella.ram.ui.util.Result.EmptyState
import com.bgargarella.ram.ui.util.Result.Success
import com.bgargarella.ram.ui.util.Result.Unknown
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update

open class BaseViewModel : ViewModel() {

    protected suspend fun <T> Flow<List<T>>.getEntitiesById(
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
            .collect { state ->
                uiState.update { prevState ->
                    prevState.copy(isLoading = true)
                }
                uiState.update { prevState ->
                    prevState.copy(
                        isLoading = false,
                        content = state,
                    )
                }
            }
    }

    protected suspend fun <T> Flow<T>.invokeFlow(
        uiState: MutableStateFlow<Result<T>>,
    ) {
        cancellable()
            .catch {
                uiState.value = Unknown(message = it.message.toString())
            }
            .flowOn(Dispatchers.IO)
            .collect { result: T ->
                uiState.value = if (result == null) {
                    EmptyState()
                } else {
                    Success(data = result)
                }
            }
    }
}