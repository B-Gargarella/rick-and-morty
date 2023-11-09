package com.bgargarella.ram.presentation.character.screen

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.presentation.util.getCharacterItemsTest

@Composable
fun CharacterImageContainer(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(4.dp)
    ) {
        content()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CharacterImageContainerPreview() {
    val entity: Character = getCharacterItemsTest().first()
    val size: Dp = 96.dp
    CharacterImageContainer(
        modifier = Modifier
            .clip(CircleShape)
            .size(size)
    ) {
        CharacterImage(image = entity.avatar)
    }
}
