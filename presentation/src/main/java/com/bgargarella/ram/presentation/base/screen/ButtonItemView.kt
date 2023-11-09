package com.bgargarella.ram.presentation.base.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.presentation.theme.Purple_6200ee

@Composable
fun ButtonItemView(text: String, action: () -> Unit) {
    val padding: Dp = dimensionResource(id = R.dimen.default_margin)
    Spacer(modifier = Modifier.height(padding))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 16.dp,
            disabledElevation = 0.dp,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple_6200ee,
            contentColor = Color.White,
        ),
        onClick = { action() }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.2.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            ),
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ButtonItemPreview() {
    ButtonItemView(
        text = "See all episodes",
        action = {}
    )
}