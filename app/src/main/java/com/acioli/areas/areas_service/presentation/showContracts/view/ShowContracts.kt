package com.acioli.areas.areas_service.presentation.showContracts.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.acioli.areas.areas_service.domain.model.Contract
import com.acioli.areas.areas_service.domain.model.request.DeleteContract
import com.acioli.areas.areas_service.presentation.Screen
import com.acioli.areas.areas_service.presentation.deleteContract.DeleteContractViewModel
import com.acioli.areas.areas_service.presentation.showContracts.ShowContractsViewModel
import com.acioli.areas.ui.theme.fontFamilyLexend
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowContracts(
    navController: NavController,
    viewModel: ShowContractsViewModel = hiltViewModel(),
    enterpriseName: String,
    deleteContractViewModel: DeleteContractViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember {SnackbarHostState()}
    val state = viewModel.state.value

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is ShowContractsViewModel.InterfaceEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(message = event.message)
                    }
                }
            }
        }

    }

    LaunchedEffect(key1 = true) {

        deleteContractViewModel.eventFlow.collectLatest { event ->
            when(event){
                is DeleteContractViewModel.InterfaceEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(event.message)
                    }
                }
            }
        }

    }

    LaunchedEffect(key1 = true) {
        viewModel.getContracts(enterpriseName)
    }

    Scaffold(
        modifier = Modifier.padding(10.dp),
        snackbarHost = {
            SnackbarHost(snackBarHostState) {data ->

                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )

            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Lista de contratos",
                    fontSize = 42.sp,
                    fontFamily = fontFamilyLexend,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = enterpriseName,
                    fontSize = 16.sp,
                    fontFamily = fontFamilyLexend,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))

            }

            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)) {
                items(state.contracts.size) {i ->
                    val contract = state.contracts[i]

                    ContractItem(
                        contract = contract,
                        onItemClick = {
                            navController.navigate(Screen.ViewUps.passEnterpriseAndContractName(enterpriseName, contract.name))
                        },
                        onTrashClick = {
                            val deleteContract = DeleteContract(contract.name, enterpriseName)
                            deleteContractViewModel.deleteContract(deleteContract)
                        }
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){

                Button(
                    onClick = {
                        viewModel.getContracts(enterpriseName)
                    }
                ) {
                    Text(text = "Visualizar contratos")
                }

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.AddContract.passEnterpriseName(enterpriseName))
                    }
                ) {
                    Text(text = "Adicionar contrato")
                }


            }

            if(state.isLoading) {
                CircularProgressIndicator()
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {

}

@Composable
fun ContractItem(contract: Contract, onItemClick: (Contract) -> Unit, onTrashClick: (Contract) -> Unit) {

    Box(modifier = Modifier.fillMaxSize()) {

        Card(
            modifier = Modifier
                .width(191.dp)
                .height(70.dp)
                .clickable {
                    onItemClick(contract)
                },
            RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF59b84b),
                contentColor = Color.White
            )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                contentAlignment = Alignment.TopStart
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = contract.name,
                        fontFamily = fontFamilyLexend,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )

                    Icon(
                        imageVector =Icons.Rounded.Delete,
                        contentDescription = "editar",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                onTrashClick(contract)
                            }
                    )

                }

            }

        }

    }

}