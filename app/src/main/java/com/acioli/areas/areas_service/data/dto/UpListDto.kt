package com.acioli.areas.areas_service.data.dto

import com.acioli.areas.areas_service.domain.model.Up

data class UpListDto(
    val name: String,
    val pilha: Int,
    val volume: Double,
    val contractName: String
){
    fun toUp(): Up {
        return Up(
            name = name,
            pilha = pilha,
            volume = volume
        )
    }
}
