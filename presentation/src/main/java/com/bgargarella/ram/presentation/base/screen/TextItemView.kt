package com.bgargarella.ram.presentation.base.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.presentation.base.model.BaseItem
import com.bgargarella.ram.presentation.base.model.TextItem
import com.bgargarella.ram.presentation.theme.Grey_989fb3
import com.bgargarella.ram.presentation.util.getCharacterItemsTest
import com.bgargarella.ram.presentation.util.getItemsList

@Composable
fun TextItemView(info: BaseItem) {
    if (!(info.text == "unknown" || info.text.isBlank())) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically),
                        maxLines = 1,
                        overflow = TextOverflow.Visible,
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
                        color = Grey_989fb3
                    )
                    val isText: Boolean = info is TextItem
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            letterSpacing = 0.2.sp,
                            textDecoration = if (isText) {
                                TextDecoration.None
                            } else {
                                TextDecoration.Underline
                            },
                            textAlign = TextAlign.Center
                        ),
                        text = info.text,
                        textAlign = TextAlign.End,
                        color = Color(if (isText) 0xFF263238 else 0xFF6200EE),
                    )
                }
            }
            RowDivider()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TextItemPreview() {
    val entities: List<Character> = getCharacterItemsTest()
    val entity: Character = entities.first()
    val items: List<BaseItem> = entity.getItemsList()
    val item: BaseItem = items.first()
    TextItemView(info = item)
}