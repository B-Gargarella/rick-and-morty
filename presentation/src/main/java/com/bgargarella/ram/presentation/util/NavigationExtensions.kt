package com.bgargarella.ram.presentation.util

import androidx.navigation.NavHostController
import com.bgargarella.ram.presentation.base.screen.BaseActivity.Companion.KEY_CHARACTERS
import com.bgargarella.ram.presentation.base.screen.BaseActivity.Companion.KEY_EPISODES
import com.bgargarella.ram.presentation.base.screen.BaseActivity.Companion.KEY_LOCATIONS

fun NavHostController.navigateToCharacters() {
    navigate(KEY_CHARACTERS)
}

fun NavHostController.navigateToCharacter(value: Int) {
    navigate("$KEY_CHARACTERS/$value")
}

fun NavHostController.navigateToEpisodesFromCharacter(value: Int) {
    navigate("$KEY_CHARACTERS/$value/$KEY_EPISODES")
}

fun NavHostController.navigateToEpisodes() {
    navigate(KEY_EPISODES)
}

fun NavHostController.navigateToEpisode(value: Int) {
    navigate("$KEY_EPISODES/$value")
}

fun NavHostController.navigateToCharactersFromEpisode(value: Int) {
    navigate("$KEY_EPISODES/$value/$KEY_CHARACTERS")
}

fun NavHostController.navigateToLocations() {
    navigate(KEY_LOCATIONS)
}

fun NavHostController.navigateToLocation(value: Int) {
    navigate("$KEY_LOCATIONS/$value")
}

fun NavHostController.navigateToCharactersFromLocation(value: Int) {
    navigate("$KEY_LOCATIONS/$value/$KEY_CHARACTERS")
}