package com.acioli.areas.areas_service.presentation.showEnterprises

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
class ShowEnterpriseViewModel @Inject constructor(
    private val repo: AreasRepository
) : ViewModel() {

    private val _state = mutableStateOf<EnterpriseState>(EnterpriseState())
    val state: State<EnterpriseState> = _state

    sealed class InterfaceEvent {
        data class ShowSnackBar(val message: String) : InterfaceEvent()
    }

    private val _eventFlow = MutableSharedFlow<InterfaceEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getAllEnterprises() {
        viewModelScope.launch {
            repo.getAllEnterprises().onEach { result ->
                when(result){
                    is Results.Loading -> {
                        _state.value = state.value.copy(
                            enterprisesName = result.data?: emptyList(),
                            isLoading = true
                        )

                    }
                    is Results.Error -> {
                        _state.value = state.value.copy(
                            enterprisesName = result.data?: emptyList(),
                            isLoading = false
                        )

                        _eventFlow.emit(
                            InterfaceEvent.ShowSnackBar(
                                message = result.message ?: "vm error"
                            )
                        )

                    }
                    is Results.Success -> {
                        _state.value = state.value.copy(
                            enterprisesName = result.data?: emptyList(),
                            isLoading = false
                        )

                        _eventFlow.emit(
                            InterfaceEvent.ShowSnackBar(
                                message = result.message ?: "vm suc error"
                            )
                        )
                    }
                }

            }.launchIn(this)
        }
    }

}