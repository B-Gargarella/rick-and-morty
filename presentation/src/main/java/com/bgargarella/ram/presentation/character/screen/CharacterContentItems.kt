package com.bgargarella.ram.presentation.character.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.presentation.base.model.BaseItem
import com.bgargarella.ram.presentation.util.getCharacterItemsTest
import com.bgargarella.ram.presentation.util.getItemsList

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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CharacterEntityContentItemsPreview() {
    val entity: Character = getCharacterItemsTest().first()
    CharacterEntityContentItems(
        modifier = Modifier.fillMaxWidth(),
        items = entity.getItemsList()
    )
}