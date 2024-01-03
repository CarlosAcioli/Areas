package com.acioli.areas.areas_service.presentation.showUp

import com.acioli.areas.areas_service.domain.model.Up

data class ShowUpState(
    val up: List<Up> = emptyList(),
    val isLoading: Boolean = false
)
