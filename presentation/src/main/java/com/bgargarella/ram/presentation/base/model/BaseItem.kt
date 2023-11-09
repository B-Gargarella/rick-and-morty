package com.bgargarella.ram.presentation.base.model

import androidx.annotation.StringRes

interface BaseItem {
    @get:StringRes
    val titleId: Int
    val text: String
}