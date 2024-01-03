package com.acioli.areas.areas_service.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.acioli.areas.areas_service.domain.model.Contract

@Entity
data class ContractEntity(
    @PrimaryKey(autoGenerate = false)
    val contractName: String,
    val enterpriseNameFromContract: String
){

    fun toContract(): Contract {
        return Contract(
            name = contractName
        )
    }

}

