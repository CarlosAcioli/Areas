package com.acioli.areas.areas_service.presentation.createContract

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acioli.areas.areas_service.domain.model.Contract
import com.acioli.areas.areas_service.domain.repository.AreasRepository
import com.acioli.areas.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateContractViewModel @Inject constructor(
    private val repository: AreasRepository
) : ViewModel() {

    private val _contractPost =  MutableStateFlow<Contract>(Contract(""))
    val contractPost: StateFlow<Contract> = _contractPost

    private val _state = mutableStateOf(ContractPostState())
    val state: State<ContractPostState> = _state

    sealed class InterfaceEvent() {
        data class ShowSnackBar(val message: String): InterfaceEvent()
    }

    private val _eventFlow = MutableSharedFlow<InterfaceEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun addContract(contract: Contract, enterpriseName: String) {

        _contractPost.value = contract

        viewModelScope.launch {

            repository.addContract(contract, enterpriseName).onEach { result ->

                when (result) {

                    is Results.Loading -> {

                        _state.value = state.value.copy(
                            isPosted = result.data?: false,
                            isLoading = true
                        )

                    }

                    is Results.Error -> {

                        _state.value = state.value.copy(
                            isPosted = result.data?: false,
                            isLoading = false
                        )

                        _eventFlow.emit(InterfaceEvent.ShowSnackBar(message = result.message?: "Erro desconhecido"))

                    }

                    is Results.Success -> {

                        _state.value = state.value.copy(
                            isPosted = result.data?: false,
                            isLoading = false
                        )

                        _eventFlow.emit(InterfaceEvent.ShowSnackBar(message = result.message?: "Erro desconhecido"))

                    }

                }

            }.launchIn(this)

        }

    }

}