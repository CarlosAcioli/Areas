package com.acioli.areas.areas_service.presentation.showUp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class ShowUpViewModel @Inject constructor (
 private val repository: AreasRepository
): ViewModel() {

    private val _state = mutableStateOf<ShowUpState>(ShowUpState())
    val state: State<ShowUpState> = _state

    sealed class InterfaceEvent {
        data class ShowSnackBar(val message: String) : InterfaceEvent()
    }

    private val _eventFlow = MutableSharedFlow<InterfaceEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getUps(enterpriseName: String, contractName: String) {

        viewModelScope.launch {

            repository.getUps(enterpriseName, contractName).onEach { result ->

                when (result) {

                    is Results.Success -> {

                        _state.value = state.value.copy(
                            up = result.data?: emptyList(),
                            isLoading = false
                        )

                        _eventFlow.emit(InterfaceEvent.ShowSnackBar(message = result.message?: "Erro desconhecido"))

                    }

                    is Results.Loading -> {

                        _state.value = state.value.copy(
                            up = result.data?: emptyList(),
                            isLoading = true
                        )

                    }

                    is Results.Error -> {

                        _state.value = state.value.copy(
                            up = result.data?: emptyList(),
                            isLoading = false
                        )

                        _eventFlow.emit(InterfaceEvent.ShowSnackBar(message = result.message?: "Erro desconhecido"))

                    }

                }

            }.launchIn(this)

        }

    }

}