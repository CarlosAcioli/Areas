package com.acioli.areas.areas_service.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.acioli.areas.areas_service.data.local.entity.ContractEntity
import com.acioli.areas.areas_service.data.local.entity.EnterpriseEntity
import com.acioli.areas.areas_service.data.local.entity.UpEntity
import com.acioli.areas.areas_service.data.local.relations.ContractWithUps
import com.acioli.areas.areas_service.data.local.relations.EnterpriseWithContracts

@Dao
interface AreasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnterprise(enterprise: EnterpriseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContract(contract: ContractEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUp(up: UpEntity)

    @Transaction
    @Query("SELECT * FROM EnterpriseEntity")
    suspend fun getEnterprises(): List<EnterpriseEntity>

    @Transaction
    @Query("SELECT * FROM EnterpriseEntity WHERE enterpriseName = :enterpriseName ")
    suspend fun getEnterpriseWithContracts(enterpriseName: String): List<EnterpriseWithContracts>

    @Transaction
    @Query("SELECT * FROM ContractEntity WHERE contractName = :contractName ")
    suspend fun getContractWithUps(contractName: String): List<ContractWithUps>

    @Transaction
    @Query("DELETE FROM ContractEntity WHERE contractName = :contractName")
    suspend fun deleteContract(contractName: String)

}