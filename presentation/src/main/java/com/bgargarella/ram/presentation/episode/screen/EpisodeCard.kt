package com.bgargarella.ram.presentation.episode.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.presentation.util.getEpisodeItemsTest
import com.bgargarella.ram.presentation.util.navigateToEpisode

@Composable
fun EpisodeCard(
    navController: NavHostController,
    entity: Episode,
) {
    val dimen: Dp = dimensionResource(id = R.dimen.default_margin)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimen)
            .clickable { navController.navigateToEpisode(entity.id) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(corner = CornerSize(dimen))
    ) {
        EpisodesContent(entity)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EpisodeCardPreview() {
    val entity: Episode = getEpisodeItemsTest().first()
    EpisodeCard(
        navController = rememberNavController(),
        entity = entity,
    )
}