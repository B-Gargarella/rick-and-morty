package com.bgargarella.ram.ui.base.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.bgargarella.ram.ui.theme.RamAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            RamAppTheme {
                MyApp()
            }
        }
    }
}