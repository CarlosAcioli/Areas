package com.acioli.areas.areas_service.presentation.createUp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.acioli.areas.areas_service.domain.model.Up
import com.acioli.areas.areas_service.domain.model.request.UpPostRequest
import com.acioli.areas.areas_service.presentation.createUp.CreateUpViewModel
import com.acioli.areas.ui.theme.fontFamilyLexend
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUp(
    navController: NavController,
    enterpriseName: String,
    contractName: String,
    viewModel: CreateUpViewModel = hiltViewModel()
) {

    var upName by remember { mutableStateOf("") }
    var upPilha by remember { mutableStateOf("") }
    var upVolume by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CreateUpViewModel.InterfaceEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(message = event.message)
                    }
                }
            }

        }

    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState) {data ->

                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )

            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Adicionar up",
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

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = contractName,
                    fontSize = 16.sp,
                    fontFamily = fontFamilyLexend,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )

            }

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = upName,
                onValueChange = { upName = it },
                singleLine = true,
                label = { Text(text = "nome") }
            )

            OutlinedTextField(
                value = upPilha,
                onValueChange = { upPilha = it },
                singleLine = true,
                label = { Text(text = "pilha") }
            )

            OutlinedTextField(
                value = upVolume,
                onValueChange = { upVolume = it },
                singleLine = true,
                label = { Text(text = "volume (m³)") }
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    val pilha = upPilha.toInt()
                    val volume = upVolume.toDouble()
                    val up = Up(upName, pilha, volume)
                    val upPostRequest = UpPostRequest(enterpriseName, contractName, up)
                    viewModel.addUp(upPostRequest)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = "Clique para adicionar up",
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
    CreateUp(rememberNavController(), "empresa tal", "contrato tal")
}