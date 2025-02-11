package br.leeloo.fourauth.presentation.action

internal sealed class HomeAction {
    data object NavigateToLogin : HomeAction()
}