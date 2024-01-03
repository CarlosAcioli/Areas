package com.acioli.areas.areas_service.data.repository

import com.acioli.areas.areas_service.data.local.AreasDao
import com.acioli.areas.areas_service.data.local.entity.ContractEntity
import com.acioli.areas.areas_service.data.local.entity.UpEntity
import com.acioli.areas.areas_service.data.remote.Api
import com.acioli.areas.areas_service.domain.model.Contract
import com.acioli.areas.areas_service.domain.model.Enterprise
import com.acioli.areas.areas_service.domain.model.Up
import com.acioli.areas.areas_service.domain.model.request.ChangeUpRequest
import com.acioli.areas.areas_service.domain.model.request.DeleteContract
import com.acioli.areas.areas_service.domain.model.request.EnterpriseName
import com.acioli.areas.areas_service.domain.model.request.UpPostRequest
import com.acioli.areas.areas_service.domain.repository.AreasRepository
import com.acioli.areas.utils.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AreasRepositoryImpl @Inject constructor(
    private val api: Api,
    private val dao: AreasDao
) : AreasRepository {

    override fun addEnterprise(enterprise: Enterprise): Flow<Results<Boolean>> {

        return flow {

            emit(Results.Loading())

            val addEnterpriseDb = dao.insertEnterprise(enterprise.toEnterpriseEntity())
            emit(Results.Success(data = true, message = "empresa adicionada"))

//            try {
//
//                val insertEnterprise = api.addEnterprise(enterprise)
//                emit(Results.Success(data = insertEnterprise, message = "empresa adicionada"))
//
//            } catch (e: HttpException) {
//
//                e.printStackTrace()
//                emit(Results.Error(message = "Não foi possível adicionar a empresa..."))
//
//            } catch (e: IOException) {
//
//                e.printStackTrace()
//                emit(Results.Error(message = "Cheque a sua conexão com a internet"))
//
//            }

        }

    }

    override fun getAllEnterprises(): Flow<Results<List<String>>> {

        return flow {

            emit(Results.Loading())

            val getEnterprises = dao.getEnterprises().map { it.enterpriseName }
            emit(Results.Success(data = getEnterprises, message = "aqui estão as empresas"))

//            try {
//
//                val enterprisesName = api.getAllEnterprises()
//
//                if (enterprisesName.isNotEmpty()) {
//                    emit(
//                        Results.Success(
//                            data = enterprisesName,
//                            message = "aqui estão as empresas!"
//                        )
//                    )
//
//                } else emit(Results.Error(message = "Não há empresas adicionada"))
//
//            } catch (e: HttpException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Não foi possível retornar as empresas"))
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Cheque a sua conexão com a internet"))
//
//            }

        }

    }

    override fun addContract(contract: Contract, enterpriseName: String): Flow<Results<Boolean>> {

        return flow {

            emit(Results.Loading())

            val contractEntity = ContractEntity(contractName = contract.name, enterpriseNameFromContract = enterpriseName)
            val addContractDb = dao.insertContract(contractEntity)
            emit(Results.Success(true, "contrato adicionado"))

//            try {
//
//                val contractAdded = api.addContract(contract, enterpriseName)
//                emit(Results.Success(data = contractAdded, message = "Contrato adicionado"))
//
//            } catch (e: HttpException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Não foi possível retornar as empresas"))
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Cheque a sua conexão com a internet"))
//
//            }

        }

    }

    override fun getContracts(enterpriseName: String): Flow<Results<List<Contract>>> {

        return flow {

            emit(Results.Loading())

            val contractsDb = dao.getEnterpriseWithContracts(enterpriseName).flatMap { it.contracts.map { it.toContract() } }
            emit(Results.Success(data = contractsDb, message = "aqui estão os contratos"))

//            try {
//
//                val contracts = api.getContracts(enterpriseName).map { it.toContract() }
//
//                emit(Results.Success(data = contracts, message = "aqui estão os contratos!"))
//
//
//            } catch (e: HttpException) {
//
//                e.printStackTrace()
//
//                if (e.code() == 404) {
//                    emit(Results.Error(message = "Ainda não há contratos nesta empresa"))
//                } else emit(Results.Error(message = "Não foi possível retornar os contratos"))
//
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Cheque a sua conexão com a internet"))
//
//            }

        }

    }

    override fun addUp(upPostRequest: UpPostRequest): Flow<Results<Boolean>> {

        return flow {

            emit(Results.Loading())

            val upEntity = UpEntity(
                upName = upPostRequest.ups.name,
                upPilha = upPostRequest.ups.pilha,
                upVolume = upPostRequest.ups.volume,
                contractNameFromUp = upPostRequest.contractName
            )
            val addUpDb = dao.insertUp(upEntity)
            emit(Results.Success(data = true, message = "Up adicionada com sucesso"))

//            try {
//
//                val upAdded = api.addUp(upPostRequest)
//                emit(Results.Success(data = upAdded, message = "Up adicionada"))
//
//            } catch (e: HttpException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Não foi possível retornar os contratos"))
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Cheque a sua conexão com a internet"))
//
//            }

        }

    }

    override fun getUps(enterpriseName: String, contractName: String): Flow<Results<List<Up>>> {

        return flow {

            emit(Results.Loading())

            val getUps = dao.getContractWithUps(contractName).flatMap { it.ups.map { it.toUp() } }
            emit(Results.Success(data = getUps, message = "aqui estão as ups"))

//            try {
//
//                val ups = api.getUps(enterpriseName, contractName).map { it.toUp() }
//
//                if (ups.isNotEmpty()) {
//                    emit(Results.Success(data = ups, message = "aqui estão as ups"))
//
//                } else emit(Results.Error(message = "Ainda não há UPS nesse contrato"))
//
//            } catch (e: HttpException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Não foi possível retornar os contratos"))
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Cheque a sua conexão com a internet"))
//
//            }

        }

    }

    override fun changeUp(changeUpRequest: ChangeUpRequest): Flow<Results<Boolean>> {

        return flow {

            emit(Results.Loading())

            val upEntity = UpEntity(
                upName = changeUpRequest.up.name ,
                upPilha = changeUpRequest.up.pilha,
                upVolume = changeUpRequest.up.volume,
                contractNameFromUp = changeUpRequest.contractName
            )
            val addUp = dao.insertUp(upEntity)
            emit(Results.Success(data = true, message = "up modificada com sucesso"))

//            try {
//
//                val upChanged = api.changeUp(changeUpRequest)
//                emit(Results.Success(data = upChanged, message = "Up modificada com sucesso"))
//
//            } catch (e: HttpException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Não foi possível modificar a up"))
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Cheque a sua conexão com a internet"))
//
//            }

        }

    }

    override fun deleteContract(deleteContract: DeleteContract): Flow<Results<Boolean>> {

        return flow {

            emit(Results.Loading())

            val deleteContractDb = dao.deleteContract(deleteContract.contractName)
            emit(Results.Success(true, message = "contrato deletado com sucesso"))

//            try {
//
//                val deleteContract = api.deleteContract(deleteContract)
//                emit(Results.Success(data = deleteContract, message = "Contrato deletado"))
//
//            } catch (e: HttpException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Não foi deletar o contrato"))
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//                emit(Results.Error(message = "Cheque a sua conexão com a internet"))
//
//            }

        }

    }
}