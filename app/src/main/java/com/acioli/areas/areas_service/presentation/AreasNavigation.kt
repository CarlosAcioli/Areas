package com.acioli.areas.areas_service.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.acioli.areas.areas_service.presentation.changeUp.view.ChangeUp
import com.acioli.areas.areas_service.presentation.createContract.view.CreateContract
import com.acioli.areas.areas_service.presentation.createEnterprise.view.AddEnterprise
import com.acioli.areas.areas_service.presentation.createUp.view.CreateUp
import com.acioli.areas.areas_service.presentation.showContracts.view.ShowContracts
import com.acioli.areas.areas_service.presentation.showEnterprises.view.ShowEnterprises
import com.acioli.areas.areas_service.presentation.showUp.view.ViewUps

@Composable
fun AreasNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.ViewEnterprises.route) {

        composable(Screen.ViewEnterprises.route) {
            ShowEnterprises(navController = navController)
        }

        composable(Screen.AddEnterprises.route) {
            AddEnterprise(navController = navController)
        }

        composable(
            route = Screen.AddContract.route,
            arguments = listOf(
                navArgument("enterpriseName") {
                    type = NavType.StringType
                }
            )
        ) {
            CreateContract(
                navController = navController,
                enterpriseName = it.arguments?.getString("enterpriseName")!!
            )
        }

        composable(
            route = Screen.ViewContracts.route,
            arguments = listOf(
                navArgument("enterpriseName") {
                    type = NavType.StringType
                }
            )
        ) {
            ShowContracts(
                navController = navController,
                enterpriseName = it.arguments?.getString("enterpriseName")!!
            )
        }

        composable(
            route = Screen.AddUp.route,
            arguments = listOf(
                navArgument("enterpriseName") {
                    type = NavType.StringType
                },
                navArgument("contractName") {
                    type = NavType.StringType
                }
            )
        ) {
            CreateUp(
                navController = navController,
                enterpriseName = it.arguments?.getString("enterpriseName")!!,
                contractName = it.arguments?.getString("contractName")!!
            )
        }

        composable(
            route = Screen.ViewUps.route,
            arguments = listOf(
                navArgument("enterpriseName") {
                    type = NavType.StringType
                },
                navArgument("contractName") {
                    type = NavType.StringType
                }
            )
        ) {
            ViewUps(
                navController = navController,
                enterpriseName = it.arguments?.getString("enterpriseName")!!,
                contractName = it.arguments?.getString("contractName")!!
            )
        }

        composable(
            route = Screen.ChangeUp.route,
            arguments = listOf(
                navArgument("enterpriseName") {
                    type = NavType.StringType
                },
                navArgument("contractName") {
                    type = NavType.StringType
                },
                navArgument("upName") {
                    type = NavType.StringType
                },
                navArgument("upPilha") {
                    type = NavType.StringType
                },
                navArgument("upVolume") {
                    type = NavType.StringType
                }
            )
        ) {
           ChangeUp(
               navController = navController,
               enterpriseName = it.arguments?.getString("enterpriseName")!!,
               contractName = it.arguments?.getString("contractName")!!,
               upNameArg = it.arguments?.getString("upName")!!,
               upPilhaArg = it.arguments?.getString("upPilha")!!,
               upVolumeArg = it.arguments?.getString("upVolume")!!
           )
        }

    }

}