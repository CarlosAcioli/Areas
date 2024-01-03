package com.acioli.areas.areas_service.domain.model.request

import com.acioli.areas.areas_service.domain.model.Up

data class UpPostRequest(
    val enterpriseName: String,
    val contractName: String,
    val ups: Up
)
