package br.leeloo.fourauth.presentation.action

internal sealed class RegisterAction {
    data object RegisterButtonClicked : RegisterAction()
    data object NavigateToHome : RegisterAction()
}