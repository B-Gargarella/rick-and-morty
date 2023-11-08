package com.bgargarella.ram.ui.base.model

import androidx.annotation.StringRes

data class TextItem(
    @get:StringRes override val titleId: Int,
    override val text: String,
) : BaseItem