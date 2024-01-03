package com.acioli.areas.areas_service.presentation

const val ENTERPRISE_NAME_TO_VIEW_CONTRACTS = "enterpriseName"
const val CONTRACT_NAME_TO_VIEW_UPS = "contractName"

sealed class Screen(val route: String) {

    object AddEnterprises : Screen("add_enterprises")
    object ViewEnterprises : Screen("view_enterprises")
    object AddContract : Screen("add_contract/{$ENTERPRISE_NAME_TO_VIEW_CONTRACTS}") {
        fun passEnterpriseName(enterpriseName: String): String {
            return "add_contract/$enterpriseName"
        }
    }
    object ViewContracts: Screen("view_contracts/{$ENTERPRISE_NAME_TO_VIEW_CONTRACTS}") {
        fun passEnterpriseName(enterpriseName: String): String {
            return "view_contracts/$enterpriseName"
        }
    }
    object AddUp: Screen("add_up/{$ENTERPRISE_NAME_TO_VIEW_CONTRACTS}/{$CONTRACT_NAME_TO_VIEW_UPS}"){
        fun passEnterpriseAndContractName(enterpriseName: String, contractName: String): String {
            return "add_up/$enterpriseName/$contractName"
        }
    }
    object ViewUps: Screen("view_ups/{$ENTERPRISE_NAME_TO_VIEW_CONTRACTS}/{$CONTRACT_NAME_TO_VIEW_UPS}"){
        fun passEnterpriseAndContractName(enterpriseName: String, contractName: String): String {
            return "view_ups/$enterpriseName/$contractName"
        }
    }
    object ChangeUp: Screen("change_up/{$ENTERPRISE_NAME_TO_VIEW_CONTRACTS}/{$CONTRACT_NAME_TO_VIEW_UPS}/{upName}/{upPilha}/{upVolume}"){
        fun passEnterpriseAndContractNameAndUpInfo(
            enterpriseName: String,
            contractName: String,
            upName: String,
            upPilha: String,
            upVolume: String
        ): String {
            return "change_up/$enterpriseName/$contractName/$upName/$upPilha/$upVolume"
        }
    }

}
