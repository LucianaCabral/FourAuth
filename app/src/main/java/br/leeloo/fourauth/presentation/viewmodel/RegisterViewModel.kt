package br.leeloo.fourauth.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.leeloo.fourauth.domain.usecase.RegisterUseCase
import br.leeloo.fourauth.presentation.action.RegisterAction
import br.leeloo.fourauth.presentation.state.RegisterState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    private val _action: Channel<RegisterAction> = Channel<RegisterAction>(Channel.CONFLATED)
    val action: Flow<RegisterAction> = _action.receiveAsFlow()

    fun onEmailChanged(email: String) {
        _state.value = state.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _state.value = state.value.copy(password = password)
    }

    fun register() {
        viewModelScope.launch(dispatcher) {
            _state.value = state.value.copy(isLoading = true)
            registerUseCase(state.value.email, state.value.password).collect { result ->
                result.onSuccess {
                    _state.value = state.value.copy(isSuccess = true, isLoading = false)
                    _action.send(RegisterAction.NavigateToHome)
                }.onFailure {
                    _state.value = state.value.copy(error = it.message, isLoading = false)
                }
            }
        }
    }

    fun onButtonRegisterClicked() {
        _action.trySend(RegisterAction.RegisterButtonClicked)
    }
}
