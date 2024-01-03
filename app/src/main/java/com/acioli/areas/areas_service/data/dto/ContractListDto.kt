package com.acioli.areas.areas_service.data.dto

import com.acioli.areas.areas_service.domain.model.Contract

data class ContractListDto(
    val contractName: String,
    val contractValue: Double,
    val enterpriseName: String
) {
    fun toContract(): Contract {
        return Contract(
            name = contractName
        )
    }
}
