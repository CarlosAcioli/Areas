package com.acioli.areas.areas_service.domain.model

import com.acioli.areas.areas_service.data.local.entity.ContractEntity

data class Contract(
    val name: String,
    val ups: List<Up> = emptyList()
)
