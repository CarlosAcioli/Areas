package com.acioli.areas.areas_service.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.acioli.areas.areas_service.domain.model.Up

@Entity
data class UpEntity(
    @PrimaryKey(autoGenerate = false)
    val upName: String,
    val upPilha: Int,
    val upVolume: Double,
    val contractNameFromUp: String
){

    fun toUp(): Up {
        return Up(
            name = upName,
            pilha = upPilha,
            volume = upVolume
        )
    }

}

