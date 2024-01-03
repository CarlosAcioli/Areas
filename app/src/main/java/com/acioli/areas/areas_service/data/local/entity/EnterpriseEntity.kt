package com.acioli.areas.areas_service.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.acioli.areas.areas_service.domain.model.Enterprise

@Entity(tableName = "EnterpriseEntity")
data class EnterpriseEntity(
    @PrimaryKey(autoGenerate = false)
    val enterpriseName: String
){
    fun toEnterprise(): Enterprise {
        return Enterprise(
            name = enterpriseName
        )
    }
}
