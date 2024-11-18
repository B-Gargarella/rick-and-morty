package com.bgargarella.ram.presentation.character.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.presentation.util.getCharacterItemsTest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterImage(image: String) {
    val openDialog = remember { mutableStateOf(false) }

    Box {
        Image(
            painter = loadImage(image = image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .clickable { openDialog.value = true }
        )
    }

    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = { openDialog.value = false }
        ) {
            Image(
                painter = loadImage(image = image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun loadImage(image: String): Painter =
    rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .placeholder(R.drawable.placeholder_character)
            .size(Size.ORIGINAL)
            .build()
    )

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CharacterImagePreview() {
    val entity: Character = getCharacterItemsTest().first()
    CharacterImage(entity.avatar)
}