package com.acioli.areas.areas_service.presentation.createContract.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.acioli.areas.areas_service.domain.model.Contract
import com.acioli.areas.areas_service.presentation.createContract.CreateContractViewModel
import com.acioli.areas.ui.theme.fontFamilyLexend
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateContract(
    navController: NavController,
    enterpriseName: String,
    viewModel: CreateContractViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var contractName by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CreateContractViewModel.InterfaceEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(message = event.message)
                    }
                }
            }

        }

    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
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
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Adicionar contrato",
                    fontSize = 40.sp,
                    fontFamily = fontFamilyLexend,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = enterpriseName,
                    fontSize = 16.sp,
                    fontFamily = fontFamilyLexend,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )

            }

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = contractName,
                onValueChange = { contractName = it },
                singleLine = true,
                label = { Text(text = "nome do contrato") }
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    val contract = Contract(name = contractName)

                    viewModel.addContract(contract, enterpriseName)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = "Clique para adicionar contrato",
                    fontFamily = fontFamilyLexend,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CreateContract(navController = rememberNavController(), enterpriseName = "Empresa tal")
}