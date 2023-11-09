package com.bgargarella.ram.presentation.base.model

import androidx.annotation.StringRes

data class ButtonItem(
    @get:StringRes override val titleId: Int,
    override val text: String,
    val id: Int
) : BaseItem