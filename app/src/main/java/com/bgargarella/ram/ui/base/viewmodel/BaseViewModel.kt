package com.bgargarella.ram.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import com.bgargarella.ram.ui.util.Result
import com.bgargarella.ram.ui.util.Result.EmptyState
import com.bgargarella.ram.ui.util.Result.Loading
import com.bgargarella.ram.ui.util.Result.Offline
import com.bgargarella.ram.ui.util.Result.Success
import com.bgargarella.ram.ui.util.Result.Unknown
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

open class BaseViewModel : ViewModel() {

    /*
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    */

    protected suspend fun <T> Flow<T>.invokeFlow2(
        uiState: MutableStateFlow<T?>,
    ) {
        cancellable()
            .catch { uiState.value = null }
            .flowOn(Dispatchers.IO)
            .collect { result: T? -> uiState.value = result }
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