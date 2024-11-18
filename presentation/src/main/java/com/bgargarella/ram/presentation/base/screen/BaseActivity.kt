package com.bgargarella.ram.presentation.base.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.presentation.base.model.UiState
import com.bgargarella.ram.presentation.character.screen.CharacterScreen
import com.bgargarella.ram.presentation.character.screen.CharactersPagedScreen
import com.bgargarella.ram.presentation.character.screen.CharactersScreen
import com.bgargarella.ram.presentation.character.viewmodel.CharactersViewModel
import com.bgargarella.ram.presentation.episode.screen.EpisodeScreen
import com.bgargarella.ram.presentation.episode.screen.EpisodesScreen
import com.bgargarella.ram.presentation.episode.viewmodel.EpisodesViewModel
import com.bgargarella.ram.presentation.location.screen.LocationScreen
import com.bgargarella.ram.presentation.location.screen.LocationsPagedScreen
import com.bgargarella.ram.presentation.location.viewmodel.LocationsViewModel
import com.bgargarella.ram.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseActivity : ComponentActivity() {

    private val charactersViewModel: CharactersViewModel by viewModels()
    private val episodesViewModel: EpisodesViewModel by viewModels()
    private val locationsViewModel: LocationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController: NavHostController = rememberNavController()
                // Get current back stack entry
                val backStackEntry by navController.currentBackStackEntryAsState()

                Scaffold(
                    topBar = {
                        AppBar(
                            title = backStackEntry.getHeader(),
                            canNavigateBack = navController.previousBackStackEntry != null,
                            navigateUp = { navController.navigateUp() }
                        )
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = KEY_START_SCREEN,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // CHARACTERS
                        composable(route = KEY_CHARACTERS) {
                            val entities: LazyPagingItems<Character> =
                                charactersViewModel.getCharacters().collectAsLazyPagingItems()

                            CharactersPagedScreen(
                                navController = navController,
                                entities = entities,
                            )
                        }
                        composable(route = KEY_CHARACTERS_FROM_EPISODE) {
                            backStackEntry?.SetView { id: Int ->
                                val state: UiState<Character> by
                                    charactersViewModel.uiState.collectAsState()

                                LaunchedEffect(key1 = null) {
                                    charactersViewModel.getCharactersByEpisode(id)
                                }

                                CharactersScreen(
                                    navController = navController,
                                    state = state,
                                )
                            }
                        }
                        composable(route = KEY_CHARACTERS_FROM_LOCATION) {
                            backStackEntry?.SetView { id: Int ->
                                val state: UiState<Character> by
                                    charactersViewModel.uiState.collectAsState()

                                LaunchedEffect(key1 = null) {
                                    charactersViewModel.getCharactersByLocation(id)
                                }

                                CharactersScreen(
                                    navController = navController,
                                    state = state,
                                )
                            }
                        }
                        // CHARACTER
                        composable(route = KEY_CHARACTER) {
                            backStackEntry?.SetView { id: Int ->
                                CharacterScreen(
                                    navController = navController,
                                    id = id
                                )
                            }
                        }
                        // EPISODES
                        composable(route = KEY_EPISODES) {
                            val entities: LazyPagingItems<Episode> =
                                episodesViewModel.getEpisodes().collectAsLazyPagingItems()

                            EpisodesScreen(
                                navController = navController,
                                entities = entities,
                            )
                        }
                        composable(route = KEY_EPISODES_FROM_CHARACTER) {
                            backStackEntry?.SetView { id: Int ->
                                val state: UiState<Episode> by
                                    episodesViewModel.uiState.collectAsState()

                                LaunchedEffect(key1 = null) {
                                    episodesViewModel.getEpisodesByCharacter(id)
                                }

                                EpisodesScreen(
                                    navController = navController,
                                    state = state,
                                )
                            }
                        }
                        composable(route = KEY_EPISODE) {
                            backStackEntry?.SetView { id: Int ->
                                EpisodeScreen(
                                    navController = navController,
                                    id = id,
                                )
                            }
                        }
                        // LOCATIONS
                        composable(route = KEY_LOCATIONS) {
                            val entities: LazyPagingItems<Location> =
                                locationsViewModel.getLocations().collectAsLazyPagingItems()

                            LocationsPagedScreen(
                                navController = navController,
                                entities = entities,
                            )
                        }
                        // LOCATION
                        composable(route = KEY_LOCATION) {
                            backStackEntry?.SetView { id: Int ->
                                LocationScreen(
                                    navController = navController,
                                    id = id,
                                )
                            }
                        }
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

    // Get the name of the current screen
    private fun NavBackStackEntry?.getHeader(): String =
        this?.arguments?.getString(KEY_ID).orEmpty().let { argument: String ->
            when (this?.destination?.route ?: KEY_START_SCREEN) {
                KEY_CHARACTERS ->
                    getString(R.string.characters)
                KEY_CHARACTER ->
                    getString(R.string.character_label, argument)
                KEY_EPISODES ->
                    getString(R.string.episodes)
                KEY_EPISODE ->
                    getString(R.string.episode_label, argument)
                KEY_LOCATIONS ->
                    getString(R.string.locations)
                KEY_LOCATION ->
                    getString(R.string.location_label, argument)
                KEY_EPISODES_FROM_CHARACTER ->
                    getString(R.string.episodes_from_character, argument)
                KEY_CHARACTERS_FROM_EPISODE ->
                    getString(R.string.characters_from_episode, argument)
                KEY_CHARACTERS_FROM_LOCATION ->
                    getString(R.string.characters_from_location, argument)
                else ->
                    getString(R.string.characters)
            }
        }

    companion object {
        private const val KEY_ID: String = "id"
        const val KEY_CHARACTERS: String = "characters"
        const val KEY_EPISODES: String = "episodes"
        const val KEY_LOCATIONS: String = "locations"
        private const val KEY_START_SCREEN: String = KEY_CHARACTERS

        private const val KEY_CHARACTER: String =
            "$KEY_CHARACTERS/{$KEY_ID}"
        private const val KEY_EPISODE: String =
            "$KEY_EPISODES/{$KEY_ID}"
        private const val KEY_LOCATION: String =
            "$KEY_LOCATIONS/{$KEY_ID}"

        private const val KEY_EPISODES_FROM_CHARACTER: String =
            "$KEY_CHARACTERS/{$KEY_ID}/$KEY_EPISODES"
        private const val KEY_CHARACTERS_FROM_EPISODE: String =
            "$KEY_EPISODES/{$KEY_ID}/$KEY_CHARACTERS"
        private const val KEY_CHARACTERS_FROM_LOCATION: String =
            "$KEY_LOCATIONS/{$KEY_ID}/$KEY_CHARACTERS"
    }
}