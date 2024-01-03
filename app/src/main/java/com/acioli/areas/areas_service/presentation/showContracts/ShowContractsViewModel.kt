package com.acioli.areas.areas_service.presentation.showContracts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acioli.areas.areas_service.domain.model.request.EnterpriseName
import com.acioli.areas.areas_service.domain.repository.AreasRepository
import com.acioli.areas.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowContractsViewModel @Inject constructor (
 private val repository: AreasRepository
): ViewModel() {

    private val _state = mutableStateOf<ShowContractsState>(ShowContractsState())
    val state: State<ShowContractsState> = _state

    sealed class InterfaceEvent {
        data class ShowSnackBar(val message: String) : InterfaceEvent()
    }

    private val _eventFlow = MutableSharedFlow<InterfaceEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getContracts(enterpriseName: String) {

        viewModelScope.launch {

            repository.getContracts(enterpriseName).onEach { result ->

                when(result) {
                    is Results.Success -> {

                        _state.value = state.value.copy(
                            contracts = result.data?: emptyList(),
                            isLoading = false
                        )

                        _eventFlow.emit(InterfaceEvent.ShowSnackBar(message = result.message?: "Erro desconnhecido"))

                    }

                    is Results.Loading -> {

                        _state.value = state.value.copy(
                            contracts = result.data?: emptyList(),
                            isLoading = true
                        )

                    }

                    is Results.Error -> {

                        _state.value = state.value.copy(
                            contracts = result.data?: emptyList(),
                            isLoading = false
                        )

                        _eventFlow.emit(InterfaceEvent.ShowSnackBar(message = result.message?: "Erro desconhecido"))

                    }
                }

            }.launchIn(this)

        }

    }

}