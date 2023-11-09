package com.bgargarella.ram.presentation.location.screen

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
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.domain.location.model.Location

@Composable
fun LocationsPagedScreen(
    navController: NavHostController,
    entities: LazyPagingItems<Location>,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = entities.loadState) {
        if (entities.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (entities.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val padding: Dp = dimensionResource(id = R.dimen.default_margin)

    Box(modifier = Modifier.fillMaxSize()) {
        if (entities.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(padding))
                }
                items(entities.itemCount) { index ->
                    entities[index]?.let { entity ->
                        LocationCard(
                            navController = navController,
                            entity = entity
                        )
                    }
                }
                item {
                    if (entities.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}