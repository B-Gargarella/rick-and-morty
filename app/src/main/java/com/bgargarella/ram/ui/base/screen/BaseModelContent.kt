package com.bgargarella.ram.ui.base.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bgargarella.ram.ui.base.model.BaseItem
import com.bgargarella.ram.ui.util.getEntityItemsTest
import com.bgargarella.ram.ui.util.getItemsList

@Composable
fun BaseModelContent(
    modifier: Modifier,
    items: List<BaseItem>
) {
    Column(modifier = modifier) {
        items.forEach { entity ->
            TextItemView(info = entity)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BaseModelContentPreview() {
    val entity = getEntityItemsTest().first()
    BaseModelContent(Modifier.fillMaxSize(), entity.getItemsList())
}