package com.bgargarella.ram.presentation.character.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.model.CharacterLocationModel
import com.bgargarella.ram.presentation.base.model.TextItem
import com.bgargarella.ram.presentation.base.screen.ButtonItemView
import com.bgargarella.ram.presentation.base.screen.RowDivider
import com.bgargarella.ram.presentation.base.screen.StateDetailView
import com.bgargarella.ram.presentation.base.screen.TextItemView
import com.bgargarella.ram.presentation.character.viewmodel.CharacterViewModel
import com.bgargarella.ram.presentation.util.filterPreviewScreen
import com.bgargarella.ram.presentation.util.getCharacterItemsTest
import com.bgargarella.ram.presentation.util.getItemsList
import com.bgargarella.ram.presentation.util.navigateToEpisodesFromCharacter
import com.bgargarella.ram.presentation.util.navigateToLocation
import com.bgargarella.ram.domain.base.model.Result

@Composable
fun CharacterScreen(
    navController: NavHostController,
    id: Int
) {
    val viewModel: CharacterViewModel = hiltViewModel()

    val uiState: State<Result<Character>> = viewModel.uiState.collectAsState()

    LaunchedEffect(null) {
        viewModel.getCharacter(id)
    }

    StateDetailView(uiState = uiState) { entity: Character ->
        CharacterContent(
            navController = navController,
            entity = entity,
        )
    }
}

@Composable
fun CharacterContent(
    navController: NavHostController,
    entity: Character,
) {
    val padding: Dp = dimensionResource(id = R.dimen.default_margin)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CharacterHeader(entity = entity)
        Spacer(modifier = Modifier.height(padding))
        RowDivider()
        val items: List<TextItem> = entity.getItemsList().filterPreviewScreen()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(items.size) { index: Int ->
                TextItemView(info = items[index])
            }
        }

        val origin: CharacterLocationModel? = entity.origin
        if (!origin?.name.isNullOrBlank()) {
            ButtonItemView(
                text = stringResource(id = R.string.from_location, origin?.name.orEmpty()),
                action = { navController.navigateToLocation(origin?.id!!) }
            )
        }

        val location: CharacterLocationModel? = entity.location
        if (!location?.name.isNullOrBlank()) {
            ButtonItemView(
                text = stringResource(id = R.string.lives_in_location, location?.name.orEmpty()),
                action = { navController.navigateToLocation(location?.id!!) }
            )
        }

        ButtonItemView(
            text = stringResource(id = R.string.see_all_episodes),
            action = { navController.navigateToEpisodesFromCharacter(entity.id) }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CharacterContentPreview() {
    val entity = getCharacterItemsTest().first()
    val navController: NavHostController = rememberNavController()
    CharacterContent(navController = navController, entity = entity)
}