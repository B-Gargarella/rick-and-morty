package com.bgargarella.ram.ui.base.screen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bgargarella.ram.R
import com.bgargarella.ram.ui.util.Result
import com.bgargarella.ram.ui.util.Result.EmptyState
import com.bgargarella.ram.ui.util.Result.Loading
import com.bgargarella.ram.ui.util.Result.Offline
import com.bgargarella.ram.ui.util.Result.Success
import com.bgargarella.ram.ui.util.Result.Unknown

@Composable
fun <T> StateView(
    uiState: Result<T>,
    success: @Composable (T) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    if (hasConnection(LocalContext.current)) {
        when (uiState) {
            is EmptyState -> EmptyOrLoadingScreen()
            is Loading -> EmptyOrLoadingScreen()
            is Unknown -> ErrorScreen(message = (uiState as? Unknown<T>)?.message.toString())
            is Offline -> snackbarHostState.ShowNoConnectionError()
            is Success -> success((uiState as? Success<T>)?.data!!)
        }
    } else {
        snackbarHostState.ShowNoConnectionError()
    }
}