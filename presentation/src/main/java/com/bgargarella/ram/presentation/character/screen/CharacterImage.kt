package com.bgargarella.ram.presentation.character.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.presentation.util.getCharacterItemsTest

@Composable
fun CharacterImage(image: String) {
    Box {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .placeholder(R.drawable.placeholder_character)
                .size(Size.ORIGINAL)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CharacterImagePreview() {
    val entity: Character = getCharacterItemsTest().first()
    CharacterImage(entity.avatar)
}