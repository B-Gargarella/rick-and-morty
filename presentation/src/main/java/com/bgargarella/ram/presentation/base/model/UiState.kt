package com.bgargarella.ram.presentation.base.model

data class UiState<T>(
    val isLoading: Boolean = false,
    val content: List<T> = emptyList(),
)