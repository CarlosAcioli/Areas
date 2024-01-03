package com.acioli.areas.areas_service.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.acioli.areas.areas_service.data.local.entity.ContractEntity
import com.acioli.areas.areas_service.data.local.entity.EnterpriseEntity

data class EnterpriseWithContracts(
    @Embedded val enterprise: EnterpriseEntity,
    @Relation(
        parentColumn = "enterpriseName",
        entityColumn = "enterpriseNameFromContract"
    )
    val contracts: List<ContractEntity>
)
