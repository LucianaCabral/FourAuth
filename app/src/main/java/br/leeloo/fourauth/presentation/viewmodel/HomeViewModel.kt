package br.leeloo.fourauth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.leeloo.fourauth.analytics.ButtonEvent
import br.leeloo.fourauth.analytics.LoginAnalytics
import br.leeloo.fourauth.domain.usecase.LogoutUseCase
import br.leeloo.fourauth.presentation.action.HomeAction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val analytics: LoginAnalytics,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _action: Channel<HomeAction> = Channel<HomeAction>(Channel.CONFLATED)
    val action: Flow<HomeAction> = _action.receiveAsFlow()

    fun logout() {
        viewModelScope.launch(dispatcher) {
            logoutUseCase()
            _action.send(HomeAction.NavigateToLogin)
        }
    }

    fun onButtonLogoutClicked() {
        analytics.logButtonClicked(ButtonEvent.LOGOUT_CLICKED)
        logout()
    }
}
