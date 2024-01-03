package com.acioli.areas.areas_service.data.remote

import com.acioli.areas.areas_service.data.dto.ContractListDto
import com.acioli.areas.areas_service.data.dto.UpListDto
import com.acioli.areas.areas_service.domain.model.Contract
import com.acioli.areas.areas_service.domain.model.Enterprise
import com.acioli.areas.areas_service.domain.model.request.ChangeUpRequest
import com.acioli.areas.areas_service.domain.model.request.DeleteContract
import com.acioli.areas.areas_service.domain.model.request.EnterpriseName
import com.acioli.areas.areas_service.domain.model.request.UpPostRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {

    @POST("/areas/add-enterprise")
    suspend fun addEnterprise(@Body enterprise: Enterprise): Boolean

    @GET("/areas/get-enterprises")
    suspend fun getAllEnterprises(): List<String>

    @POST("/areas/add-contract/{enterpriseName}")
    suspend fun addContract(
        @Body contract: Contract,
        @Path("enterpriseName") enterpriseName: String
    ): Boolean

    @GET("/areas/get-contracts/{enterpriseName}")
    suspend fun getContracts(@Path("enterpriseName") enterpriseName: String): List<ContractListDto>

    @POST("/areas/add-up")
    suspend fun addUp(@Body upPostRequest: UpPostRequest): Boolean

    @GET("/areas/get-up/{enterpriseName}/{contractName}")
    suspend fun getUps(
        @Path("enterpriseName") enterpriseName: String,
        @Path("contractName") contractName: String
    ): List<UpListDto>

    @PUT("areas/change-up")
    suspend fun changeUp(@Body changeUpRequest: ChangeUpRequest): Boolean

    @HTTP(method = "DELETE", path = "/areas/delete-contract", hasBody = true)
    suspend fun deleteContract(@Body deleteContract: DeleteContract): Boolean


}