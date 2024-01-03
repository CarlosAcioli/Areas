package com.acioli.areas.areas_service.presentation.showEnterprises

data class EnterpriseState(
    val enterprisesName: List<String> = emptyList(),
    val isLoading: Boolean = false
)
