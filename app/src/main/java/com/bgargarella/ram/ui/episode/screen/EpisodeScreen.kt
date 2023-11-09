package com.bgargarella.ram.ui.episode.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bgargarella.ram.R
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.ui.base.screen.ButtonItemView
import com.bgargarella.ram.ui.base.screen.StateDetailView
import com.bgargarella.ram.ui.base.screen.TextItemView
import com.bgargarella.ram.ui.episode.viewmodel.EpisodeViewModel
import com.bgargarella.ram.ui.util.Result
import com.bgargarella.ram.ui.util.getItemsList
import com.bgargarella.ram.ui.util.navigateToCharactersFromEpisode
import kotlinx.coroutines.CoroutineScope

@Composable
fun EpisodeScreen(
    navController: NavHostController,
    id: Int,
) {

    val viewModel: EpisodeViewModel = hiltViewModel()

    val scope: CoroutineScope = rememberCoroutineScope()

    val uiState: State<Result<Episode>> = viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = scope) {
        viewModel.getEpisode(id)
    }

    StateDetailView(uiState = uiState) { entity: Episode ->
        EpisodeContent(
            navController = navController,
            entity = entity,
        )
    }
}

@Composable
fun EpisodeContent(
    navController: NavHostController,
    entity: Episode,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val items = entity.getItemsList()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(items.size) { index: Int ->
                TextItemView(info = items[index])
            }
        }

        val ids: List<Int> = entity.characters
        if (ids.isNotEmpty()) {
            ButtonItemView(
                text = stringResource(id = R.string.see_all_characters),
                action = { navController.navigateToCharactersFromEpisode(entity.id) }
            )
        }
    }
}