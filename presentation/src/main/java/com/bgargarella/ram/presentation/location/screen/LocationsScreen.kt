package com.bgargarella.ram.presentation.location.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.presentation.base.model.UiState

@Composable
fun LocationsScreen(
    navController: NavHostController,
    state: UiState<Location>,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            val padding: Dp = dimensionResource(id = R.dimen.default_margin)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(padding))
                }
                items(state.content.size) { index ->
                    LocationCard(
                        navController = navController,
                        entity = state.content[index]
                    )
                }
            }
        }
    }
}