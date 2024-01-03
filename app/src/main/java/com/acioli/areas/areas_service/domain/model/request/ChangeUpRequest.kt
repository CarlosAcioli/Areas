package com.acioli.areas.areas_service.domain.model.request

import com.acioli.areas.areas_service.domain.model.Up

data class ChangeUpRequest(
    val up: Up,
    val enterpriseName: String,
    val contractName: String
)
