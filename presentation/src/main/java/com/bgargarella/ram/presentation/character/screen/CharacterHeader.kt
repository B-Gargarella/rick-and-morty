package com.bgargarella.ram.presentation.character.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.presentation.util.getCharacterItemsTest

@Composable
fun CharacterHeader(entity: Character) {
    val size: Dp = 96.dp
    val padding: Dp = dimensionResource(id = R.dimen.default_margin)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CharacterImageContainer(
            modifier = Modifier
                .clip(CircleShape)
                .size(size)
        ) {
            CharacterImage(image = entity.avatar)
        }
        Spacer(modifier = Modifier.height(padding))
        Text(
            modifier = Modifier
                .width(size)
                .wrapContentHeight(),
            text = entity.name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CharacterHeaderPreview() {
    val entity: Character = getCharacterItemsTest().first()
    CharacterHeader(entity = entity)
}