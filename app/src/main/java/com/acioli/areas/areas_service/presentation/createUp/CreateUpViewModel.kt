package com.acioli.areas.areas_service.presentation.createUp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acioli.areas.areas_service.domain.model.Up
import com.acioli.areas.areas_service.domain.model.request.UpPostRequest
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
class CreateUpViewModel @Inject constructor (
 private val repository: AreasRepository
): ViewModel() {

    private val _upPost = MutableStateFlow<Up>(Up("", 0, 0.0))
    val upPost: StateFlow<Up> = _upPost

    private val _state = mutableStateOf<CreateUpState>(CreateUpState())
    val state: State<CreateUpState> = _state

    sealed class InterfaceEvent() {
        data class ShowSnackBar(val message: String): InterfaceEvent()
    }

    private val _eventFlow = MutableSharedFlow<InterfaceEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun addUp(upPostRequest: UpPostRequest) {

        _upPost.value = upPostRequest.ups

        viewModelScope.launch {

            repository.addUp(upPostRequest).onEach { result ->

                when (result) {

                    is Results.Success -> {

                        _state.value = state.value.copy(
                            isPosted = result.data?: false,
                            isLoading = false
                        )

                        _eventFlow.emit(InterfaceEvent.ShowSnackBar(message = result.message?: "Erro desconhecido"))

                    }

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
                }

            }.launchIn(this)

        }

    }

}