package com.bgargarella.ram.ui.character.screen

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
