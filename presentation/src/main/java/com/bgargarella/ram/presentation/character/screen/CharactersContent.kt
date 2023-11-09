package com.bgargarella.ram.presentation.character.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.presentation.util.getCharacterItemsTest
import com.bgargarella.ram.presentation.util.getItemsList

@Composable
fun CharactersContent(entity: Character) {
    val padding: Dp = dimensionResource(id = R.dimen.default_margin)
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(padding)
    ) {
        CharacterHeader(entity = entity)
        Spacer(modifier = Modifier.width(padding))
        CharacterEntityContentItems(
            modifier = Modifier.fillMaxWidth(),
            items = entity.getItemsList()
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CharactersContentPreview() {
    val entity: Character = getCharacterItemsTest().first()
    CharactersContent(entity)
}
