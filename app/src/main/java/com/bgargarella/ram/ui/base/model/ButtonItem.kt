package com.bgargarella.ram.ui.base.model

import androidx.annotation.StringRes

data class ButtonItem(
    @get:StringRes override val titleId: Int,
    override val text: String,
    val id: Int
) : BaseItem