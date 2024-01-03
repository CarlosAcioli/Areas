package com.acioli.areas.areas_service.domain.model

import com.acioli.areas.areas_service.data.local.entity.EnterpriseEntity


data class Enterprise(
    val name: String,
    val contracts: List<Contract> = emptyList()
){
    fun toEnterpriseEntity(): EnterpriseEntity {
        return EnterpriseEntity(
            enterpriseName = name
        )
    }
}
