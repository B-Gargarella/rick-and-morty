package com.bgargarella.ram.presentation.location.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.presentation.base.screen.ButtonItemView
import com.bgargarella.ram.presentation.base.screen.StateDetailView
import com.bgargarella.ram.presentation.base.screen.TextItemView
import com.bgargarella.ram.presentation.location.viewmodel.LocationViewModel
import com.bgargarella.ram.presentation.util.getItemsList
import com.bgargarella.ram.presentation.util.getLocationItemsTest
import com.bgargarella.ram.presentation.util.navigateToCharactersFromLocation
import kotlinx.coroutines.CoroutineScope
import com.bgargarella.ram.domain.base.model.Result

@Composable
fun LocationScreen(
    navController: NavHostController,
    id: Int,
) {

    val viewModel: LocationViewModel = hiltViewModel()

    val scope: CoroutineScope = rememberCoroutineScope()

    val uiState: State<Result<Location>> = viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = scope) {
        viewModel.getLocation(id)
    }

    StateDetailView(uiState = uiState) { entity: Location ->
        LocationContent(
            navController = navController,
            entity = entity,
        )
    }
}

@Composable
fun LocationContent(
    navController: NavHostController,
    entity: Location,
) {
    val padding: Dp = dimensionResource(id = R.dimen.default_margin)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(padding),
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

        val ids: List<Int> = entity.residents
        if (ids.isNotEmpty()) {
            ButtonItemView(
                text = stringResource(id = R.string.see_all_residents),
                action = { navController.navigateToCharactersFromLocation(entity.id) }
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LocationContentPreview() {
    val entity = getLocationItemsTest().first()
    val navController: NavHostController = rememberNavController()
    LocationContent(navController = navController, entity = entity)
}