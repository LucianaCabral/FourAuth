package br.leeloo.fourauth.presentation.state

internal data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isSuccess: Boolean = false,
    val error: String? = null
)
