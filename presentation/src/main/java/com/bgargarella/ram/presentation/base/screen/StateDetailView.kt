package com.bgargarella.ram.presentation.base.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.model.Result.EmptyState
import com.bgargarella.ram.domain.base.model.Result.Loading
import com.bgargarella.ram.domain.base.model.Result.Offline
import com.bgargarella.ram.domain.base.model.Result.Success
import com.bgargarella.ram.domain.base.model.Result.Unknown

@Composable
fun <T> StateDetailView(
    uiState: State<Result<T>>,
    success: @Composable (T) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    if (hasConnection(LocalContext.current)) {
        when (uiState.value) {
            is EmptyState<T> -> EmptyOrLoadingScreen()
            is Loading<T> -> EmptyOrLoadingScreen()
            is Unknown<T> -> ErrorScreen(message = (uiState.value as? Unknown<T>)?.message.toString())
            is Offline<T> -> snackbarHostState.ShowNoConnectionError()
            is Success<T> -> success((uiState.value as? Success<T>)?.data!!)
        }
    } else {
        snackbarHostState.ShowNoConnectionError()
    }
}