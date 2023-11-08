package com.bgargarella.ram.ui.character.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bgargarella.ram.ui.base.model.BaseItem

@Composable
fun CharacterEntityContentItems(
    modifier: Modifier,
    items: List<BaseItem>
) {
    Column(modifier = modifier) {
        items.forEachIndexed { index, entity ->
            CharacterTextItem(
                info = entity,
                isFirst = index == 0,
                isLast = index == items.size - 1,
            )
        }
    }
}