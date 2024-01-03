package com.acioli.areas.areas_service.presentation.createEnterprise

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acioli.areas.areas_service.domain.model.Enterprise
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
class CreateEnterpriseViewModel @Inject constructor (
 private val repository: AreasRepository
): ViewModel() {

    private val _enterprisePost = MutableStateFlow<Enterprise>(Enterprise(""))
    val enterprisePost: StateFlow<Enterprise> = _enterprisePost

    private val _state = mutableStateOf<EnterprisePostState>(EnterprisePostState())
    val state: State<EnterprisePostState> = _state

    sealed class InterfaceEvent(){
        data class ShowSnackBar(val message: String): InterfaceEvent()
    }

    private val _eventFlow = MutableSharedFlow<InterfaceEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun addEnterprise(enterprise: Enterprise) {

        _enterprisePost.value = enterprise

        viewModelScope.launch {

            repository.addEnterprise(enterprise).onEach { result->

                when (result) {

                    is Results.Success -> {
                        _state.value = state.value.copy(
                            postStatus = result.data?: false,
                            isLoading = false
                        )

                        _eventFlow.emit(InterfaceEvent.ShowSnackBar(message = result.message?: "Erro desconhecido"))
                    }

                    is Results.Error -> {
                        _state.value = state.value.copy(
                            postStatus = result.data?: false,
                            isLoading = false
                        )

                        _eventFlow.emit(InterfaceEvent.ShowSnackBar(message = result.message?: "Erro desconhecido"))

                    }

                    is Results.Loading -> {
                        _state.value = state.value.copy(
                            postStatus = result.data?: false,
                            isLoading = true
                        )
                    }

                }

            }.launchIn(this)

        }

    }

}