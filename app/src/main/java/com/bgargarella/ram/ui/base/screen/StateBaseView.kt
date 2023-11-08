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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bgargarella.ram.R

@Composable
fun SnackbarHostState.ShowNoConnectionError() {
    val message: String = stringResource(id = R.string.no_connection_error)
    LaunchedEffect(Unit) {
        showSnackbar(
            message = message
        )
    }
}

fun hasConnection(context: Context): Boolean {
    (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).let { cm ->
        cm.getNetworkCapabilities(cm.activeNetwork)?.let { capabilities ->
            return listOf(
                NetworkCapabilities.TRANSPORT_BLUETOOTH,
                NetworkCapabilities.TRANSPORT_CELLULAR,
                NetworkCapabilities.TRANSPORT_ETHERNET,
                NetworkCapabilities.TRANSPORT_WIFI
            ).any(capabilities::hasTransport)
        }
    }
    return false
}

@Composable
fun EmptyOrLoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String = "Unknown error occurred") {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Error found - $message",
            modifier = Modifier.padding(16.dp)
        )
    }
}