package br.leeloo.fourauth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.leeloo.fourauth.analytics.ButtonEvent
import br.leeloo.fourauth.analytics.LoginAnalytics
import br.leeloo.fourauth.domain.usecase.LoginUseCase
import br.leeloo.fourauth.presentation.action.LoginAction
import br.leeloo.fourauth.presentation.state.LoginState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

const val MIN_PASSWORD_LENGTH = 6

internal class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val analytics: LoginAnalytics

    ) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _uiState.asStateFlow()
    private val _action: Channel<LoginAction> = Channel<LoginAction>(Channel.CONFLATED)
    val action: Flow<LoginAction> = _action.receiveAsFlow()

    init {
        analytics.screenView()
    }

    fun onButtonRegisterClicked() {
        _action.trySend(LoginAction.onResgisterClicked)
        analytics.logButtonClicked(ButtonEvent.REGISTER_CLICKED)
    }

    fun onButtonLoginClicked() {
        _action.trySend(LoginAction.LoginButtonClicked)
        analytics.logButtonClicked(ButtonEvent.LOGIN_CLICKED)
    }


    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login() {
        val email = state.value.email
        val password = state.value.password

        when {
            !isValidEmail(email) -> handleStateEmail()
            !isValidPassword(password) -> handleStatePasswordPassword()

            else ->
                viewModelScope.launch {
                    _uiState.value = state.value.copy(isLoading = true)
                    loginUseCase(state.value.email, state.value.password).collect { result ->
                        result.onSuccess {
                            onSuccessfulLogin()
                            _action.send(LoginAction.NavigateToHome)
                            clearFields()
                        }.onFailure {
                            onFailureLogin()
                        }
                    }
                }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH
    }

    private fun handleStateEmail() {
        _uiState.value = _uiState.value.copy(error = "Email inválido ou registrar email", isLoading = false)
    }

    private fun handleStatePasswordPassword() {
        _uiState.value = _uiState.value.copy(error = "Senha inválida", isLoading = false)
    }

    private fun onFailureLogin() {
        _uiState.value = state.value.copy(isLoading = false, error = "Erro no login")
    }

    private fun onSuccessfulLogin() {
        _uiState.value = state.value.copy(isLoading = false, error = null)
    }

    fun clearFields() {
        _uiState.value = state.value.copy(email = "", password = "")
    }

    fun resetState() {
        _uiState.value = LoginState()
    }
}
