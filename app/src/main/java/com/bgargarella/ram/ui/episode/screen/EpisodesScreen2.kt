package com.bgargarella.ram.ui.episode.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bgargarella.ram.R
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.ui.episode.viewmodel.EpisodesState2

@Composable
fun EpisodesScreen2(
    navController: NavHostController,
    state: EpisodesState2,
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
                    EpisodeCard(
                        navController = navController,
                        entity = state.content[index]
                    )
                }
            }
        }
    }
}