package com.bgargarella.ram.presentation.base.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bgargarella.ram.presentation.base.model.BaseItem
import com.bgargarella.ram.presentation.util.getCharacterItemsTest
import com.bgargarella.ram.presentation.util.getItemsList

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
    val entity = getCharacterItemsTest().first()
    BaseModelContent(Modifier.fillMaxSize(), entity.getItemsList())
}