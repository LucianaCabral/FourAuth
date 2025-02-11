package br.leeloo.fourauth.presentation.action

internal sealed class LoginAction {
    data object LoginButtonClicked : LoginAction()
    data object onResgisterClicked : LoginAction()
    data object NavigateToHome : LoginAction()
}