package com.acioli.areas.areas_service.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.acioli.areas.areas_service.data.local.entity.ContractEntity
import com.acioli.areas.areas_service.data.local.entity.UpEntity

data class ContractWithUps(
    @Embedded val contract: ContractEntity,
    @Relation(
        parentColumn = "contractName",
        entityColumn = "contractNameFromUp"
    )
    val ups: List<UpEntity>
)
