package com.acioli.areas.areas_service.domain.repository

import com.acioli.areas.areas_service.domain.model.Contract
import com.acioli.areas.areas_service.domain.model.Enterprise
import com.acioli.areas.areas_service.domain.model.Up
import com.acioli.areas.areas_service.domain.model.request.ChangeUpRequest
import com.acioli.areas.areas_service.domain.model.request.DeleteContract
import com.acioli.areas.areas_service.domain.model.request.UpPostRequest
import com.acioli.areas.utils.Results
import kotlinx.coroutines.flow.Flow

interface AreasRepository {

    fun addEnterprise(enterprise: Enterprise): Flow<Results<Boolean>>

    fun getAllEnterprises(): Flow<Results<List<String>>>

    fun addContract(contract: Contract, enterpriseName: String): Flow<Results<Boolean>>

    fun getContracts(enterpriseName: String): Flow<Results<List<Contract>>>

    fun addUp(upPostRequest: UpPostRequest): Flow<Results<Boolean>>

    fun getUps(enterpriseName: String, contractName: String): Flow<Results<List<Up>>>

    fun changeUp(changeUpRequest: ChangeUpRequest): Flow<Results<Boolean>>

    fun deleteContract(deleteContract: DeleteContract): Flow<Results<Boolean>>
}