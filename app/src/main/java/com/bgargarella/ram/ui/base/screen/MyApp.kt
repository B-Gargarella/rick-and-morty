package com.bgargarella.ram.ui.base.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.ui.base.model.UiState
import com.bgargarella.ram.ui.character.screen.CharacterScreen
import com.bgargarella.ram.ui.character.screen.CharactersPagedScreen
import com.bgargarella.ram.ui.character.screen.CharactersScreen
import com.bgargarella.ram.ui.character.viewmodel.CharactersViewModel
import com.bgargarella.ram.ui.episode.screen.EpisodeScreen
import com.bgargarella.ram.ui.episode.screen.EpisodesScreen
import com.bgargarella.ram.ui.episode.viewmodel.EpisodesViewModel
import com.bgargarella.ram.ui.location.screen.LocationScreen
import com.bgargarella.ram.ui.location.screen.LocationsPagedScreen
import com.bgargarella.ram.ui.location.viewmodel.LocationsViewModel
import com.bgargarella.ram.ui.theme.RamAppTheme

const val KEY_ID: String = "id"
const val KEY_CHARACTERS: String = "characters"
const val KEY_EPISODES: String = "episodes"
const val KEY_LOCATIONS: String = "locations"
const val KEY_START_SCREEN: String = KEY_CHARACTERS

const val KEY_CHARACTER = "$KEY_CHARACTERS/{$KEY_ID}"
const val KEY_EPISODE = "$KEY_EPISODES/{$KEY_ID}"
const val KEY_LOCATION = "$KEY_LOCATIONS/{$KEY_ID}"

const val KEY_EPISODES_FROM_CHARACTER: String = "$KEY_CHARACTERS/{$KEY_ID}/$KEY_EPISODES"
const val KEY_CHARACTERS_FROM_EPISODE: String = "$KEY_EPISODES/{$KEY_ID}/$KEY_CHARACTERS"
const val KEY_CHARACTERS_FROM_LOCATION: String = "$KEY_LOCATIONS/{$KEY_ID}/$KEY_CHARACTERS"

@Composable
fun MyApp() {
    val navController: NavHostController = rememberNavController()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen: String = backStackEntry?.destination?.route ?: KEY_START_SCREEN

    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = KEY_START_SCREEN,
            modifier = Modifier.padding(innerPadding)
        ) {
            // CHARACTERS
            composable(route = KEY_CHARACTERS) {
                val viewModel: CharactersViewModel = hiltViewModel()

                val entities: LazyPagingItems<Character> =
                    viewModel.getCharacters().collectAsLazyPagingItems()

                CharactersPagedScreen(
                    navController = navController,
                    entities = entities,
                )
            }
            composable(route = KEY_CHARACTERS_FROM_EPISODE) { navBackStackEntry ->
                navBackStackEntry.SetView { id: Int ->
                    val viewModel: CharactersViewModel = hiltViewModel()

                    val state: UiState<Character> by viewModel.uiState.collectAsState()

                    LaunchedEffect(null) {
                        viewModel.getCharactersByEpisode(id)
                    }

                    CharactersScreen(
                        navController = navController,
                        state = state,
                    )
                }
            }
            composable(route = KEY_CHARACTERS_FROM_LOCATION) { navBackStackEntry ->
                navBackStackEntry.SetView { id: Int ->
                    val viewModel: CharactersViewModel = hiltViewModel()

                    val state: UiState<Character> by viewModel.uiState.collectAsState()

                    LaunchedEffect(null) {
                        viewModel.getCharactersByLocation(id)
                    }

                    CharactersScreen(
                        navController = navController,
                        state = state,
                    )
                }
            }
            // CHARACTER
            composable(route = KEY_CHARACTER) { navBackStackEntry ->
                navBackStackEntry.SetView { id: Int ->
                    CharacterScreen(
                        navController = navController,
                        id = id
                    )
                }
            }
            // EPISODES
            composable(route = KEY_EPISODES) {
                val viewModel: EpisodesViewModel = hiltViewModel()

                val entities: LazyPagingItems<Episode> =
                    viewModel.getEpisodes().collectAsLazyPagingItems()

                EpisodesScreen(
                    navController = navController,
                    entities = entities,
                )
            }
            composable(route = KEY_EPISODES_FROM_CHARACTER) { navBackStackEntry ->
                navBackStackEntry.SetView { id: Int ->
                    val viewModel: EpisodesViewModel = hiltViewModel()

                    val state: UiState<Episode> by viewModel.uiState.collectAsState()

                    LaunchedEffect(null) {
                        viewModel.getEpisodesByCharacter(id)
                    }

                    EpisodesScreen(
                        navController = navController,
                        state = state,
                    )
                }
            }
            composable(route = KEY_EPISODE) { navBackStackEntry ->
                navBackStackEntry.SetView { id: Int ->
                    EpisodeScreen(
                        navController = navController,
                        id = id,
                    )
                }
            }
            // LOCATIONS
            composable(route = KEY_LOCATIONS) {
                val viewModel: LocationsViewModel = hiltViewModel()

                val entities: LazyPagingItems<Location> =
                    viewModel.getLocations().collectAsLazyPagingItems()

                LocationsPagedScreen(
                    navController = navController,
                    entities = entities,
                )
            }
            // LOCATION
            composable(route = KEY_LOCATION) { navBackStackEntry ->
                navBackStackEntry.SetView { id: Int ->
                    LocationScreen(
                        navController = navController,
                        id = id,
                    )
                }
            }
        }
    }
}

@Composable
private fun NavBackStackEntry.SetView(viewAction: @Composable (Int) -> Unit) {
    arguments?.getString(KEY_ID)?.let { id: String? ->
        if (!id.isNullOrBlank()) {
            viewAction(id.toInt())
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    RamAppTheme(darkTheme = false) {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    RamAppTheme(darkTheme = true) {
        MyApp()
    }
}