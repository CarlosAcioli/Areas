package com.acioli.areas.areas_service.presentation.showContracts

import com.acioli.areas.areas_service.domain.model.Contract

data class ShowContractsState(
    val contracts: List<Contract> = emptyList(),
    val isLoading: Boolean = false
)
