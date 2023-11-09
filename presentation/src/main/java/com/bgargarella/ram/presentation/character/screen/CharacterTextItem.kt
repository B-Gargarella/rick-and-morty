package com.bgargarella.ram.presentation.character.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.presentation.base.model.BaseItem
import com.bgargarella.ram.presentation.base.screen.RowDivider
import com.bgargarella.ram.presentation.util.getCharacterItemsTest
import com.bgargarella.ram.presentation.util.getItemsList

@Composable
fun CharacterTextItem(
    info: BaseItem,
    isFirst: Boolean,
    isLast: Boolean,
) {
    if (!(info.text == "unknown" || info.text.isBlank())) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically),
            verticalArrangement = Arrangement.Center,
        ) {
            if (!isFirst) {
                Spacer(modifier = Modifier.height(4.dp))
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.2.sp,
                    textDecoration = TextDecoration.None,
                    textAlign = TextAlign.Center
                ),
                text = stringResource(info.titleId),
                textAlign = TextAlign.Start,
                color = Color(0xFF989FB3),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.2.sp,
                    textDecoration = TextDecoration.None,
                    textAlign = TextAlign.Center
                ),
                text = info.text,
                textAlign = TextAlign.Start,
                color = Color(0xFF263238),
            )
            if (!isLast) {
                Spacer(modifier = Modifier.height(4.dp))
                RowDivider()
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CharacterTextItemPreview() {
    val entities: List<Character> = getCharacterItemsTest()
    val entity: Character = entities.first()
    val items: List<BaseItem> = entity.getItemsList()
    val item: BaseItem = items.first()
    CharacterTextItem(info = item, false, false)
}