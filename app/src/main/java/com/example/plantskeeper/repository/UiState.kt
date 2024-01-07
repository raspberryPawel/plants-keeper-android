package com.example.plantskeeper.repository

data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)