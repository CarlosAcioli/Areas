package com.acioli.areas.areas_service.presentation.showUp.view

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
import androidx.compose.material.icons.rounded.Create
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
import com.acioli.areas.areas_service.domain.model.Up
import com.acioli.areas.areas_service.presentation.Screen
import com.acioli.areas.areas_service.presentation.showContracts.ShowContractsViewModel
import com.acioli.areas.areas_service.presentation.showContracts.view.ContractItem
import com.acioli.areas.areas_service.presentation.showUp.ShowUpViewModel
import com.acioli.areas.ui.theme.fontFamilyLexend
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewUps(
    navController: NavController,
    enterpriseName: String,
    contractName: String,
    viewModel: ShowUpViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val state = viewModel.state.value

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is ShowUpViewModel.InterfaceEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(message = event.message)
                    }
                }
            }
        }

    }

    LaunchedEffect(key1 = true) {
        viewModel.getUps(enterpriseName, contractName)
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
                    text = "Lista de Up",
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

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = contractName,
                    fontSize = 16.sp,
                    fontFamily = fontFamilyLexend,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(4.dp))

            }

            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)) {
                items(state.up.size) {i ->
                    val up = state.up[i]

                    UpItem(
                        up = up,
                        onItemClick = {
                            navController.navigate(
                                Screen.ChangeUp.passEnterpriseAndContractNameAndUpInfo(
                                    enterpriseName, contractName, up.name, up.pilha.toString(), up.volume.toString()
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = {
                        viewModel.getUps(enterpriseName, contractName)
                    }
                ) {
                    Text(text = "Visualizar ups")
                }

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.AddUp.passEnterpriseAndContractName(enterpriseName, contractName))
                    }
                ) {
                    Text(text = "Adicionar up")
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
fun UpItem(up: Up, onItemClick: (Up) -> Unit) {

    Box(modifier = Modifier.fillMaxSize()) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable {

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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "nome: ${up.name}",
                        fontFamily = fontFamilyLexend,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )

                    Text(
                        text = "pilha: ${up.pilha}",
                        fontFamily = fontFamilyLexend,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )

                    Text(
                        text = "volume: ${up.volume}",
                        fontFamily = fontFamilyLexend,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )

                    Icon(
                        imageVector =Icons.Rounded.Create,
                        contentDescription = "editar",
                        modifier = Modifier.size(30.dp)
                            .clickable {
                                onItemClick(up)
                            }
                    )
                }


            }

        }

    }

}

