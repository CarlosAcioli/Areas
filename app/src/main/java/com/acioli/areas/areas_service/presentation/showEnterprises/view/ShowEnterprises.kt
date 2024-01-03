package com.acioli.areas.areas_service.presentation.showEnterprises.view

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.acioli.areas.areas_service.presentation.Screen
import com.acioli.areas.areas_service.presentation.showEnterprises.ShowEnterpriseViewModel
import com.acioli.areas.ui.theme.fontFamilyLexend
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowEnterprises(
    viewModel: ShowEnterpriseViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ShowEnterpriseViewModel.InterfaceEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(message = event.message)
                    }
                }
            }
        }

    }

    LaunchedEffect(key1 = true) {
        viewModel.getAllEnterprises()
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {

                Text(
                    text = "Empresas",
                    fontSize = 42.sp,
                    fontFamily = fontFamilyLexend,
                    fontWeight = FontWeight.SemiBold
                )

            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
            ) {
                items(state.enterprisesName.size) { i ->
                    val name = state.enterprisesName[i]

                    EnterpriseItem(
                        enterpriseName = name,
                        onItemClick = { enterpriseName ->
                            navController.navigate(
                                Screen.ViewContracts.passEnterpriseName(
                                    enterpriseName
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
                        viewModel.getAllEnterprises()
                    }
                ) {
                    Text(text = "Visualizar empresas")
                }

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.AddEnterprises.route)
                    }
                ) {
                    Text(text = "Adicionar empresa")
                }

            }

            if (state.isLoading) {
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
fun EnterpriseItem(enterpriseName: String, onItemClick: (String) -> Unit) {

    Box(modifier = Modifier.fillMaxSize()) {

        Card(
            modifier = Modifier
                .width(181.dp)
                .height(70.dp)
                .clickable {
                    onItemClick(enterpriseName)
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
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = enterpriseName,
                    fontFamily = fontFamilyLexend,
                    fontWeight = FontWeight.Medium,
                    fontSize = 23.sp
                )

            }

        }

    }

}