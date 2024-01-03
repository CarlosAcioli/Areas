package com.acioli.areas.areas_service.domain.model

import com.acioli.areas.areas_service.data.local.entity.UpEntity

data class Up(
    val name: String,
    val pilha: Int,
    val volume: Double
)
