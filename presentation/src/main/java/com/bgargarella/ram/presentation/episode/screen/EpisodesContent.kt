package com.bgargarella.ram.presentation.episode.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.presentation.base.screen.BaseModelContent
import com.bgargarella.ram.presentation.util.filterPreviewScreen
import com.bgargarella.ram.presentation.util.getEpisodeItemsTest
import com.bgargarella.ram.presentation.util.getItemsList

@Composable
fun EpisodesContent(entity: Episode) {
    val dimen: Dp = dimensionResource(id = R.dimen.default_margin)
    BaseModelContent(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimen),
        items = entity.getItemsList().filterPreviewScreen()
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EpisodesContentPreview() {
    val entity: Episode = getEpisodeItemsTest().first()
    EpisodesContent(entity)
}