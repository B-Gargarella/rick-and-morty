package com.bgargarella.ram.ui.location.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.bgargarella.ram.R
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.ui.base.screen.BaseModelContent
import com.bgargarella.ram.ui.util.filterPreviewScreen
import com.bgargarella.ram.ui.util.getItemsList

@Composable
fun LocationsContent(entity: Location) {
    val dimen: Dp = dimensionResource(id = R.dimen.default_margin)
    BaseModelContent(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimen),
        items = entity.getItemsList().filterPreviewScreen()
    )
}