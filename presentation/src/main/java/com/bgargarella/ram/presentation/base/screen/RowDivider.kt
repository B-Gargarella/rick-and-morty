package com.bgargarella.ram.presentation.base.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowDivider() {
    Divider(
        color = Color(0xFFE6EAF5),
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    )
}