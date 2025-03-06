package br.leeloo.fourauth.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import br.leeloo.fourauth.R
import br.leeloo.fourauth.presentation.action.HomeAction
import br.leeloo.fourauth.presentation.component.HomeScreenContent
import br.leeloo.fourauth.presentation.viewmodel.HomeViewModel


@Composable
internal fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        viewModel.action.collect { action ->
            when (action) {
                is HomeAction.NavigateToLogin -> {
                    navController.navigate(context.getString(R.string.nav_login)) {
                        popUpTo(context.getString(R.string.nav_home)) { inclusive = true }
                    }
                }
            }
        }
    }
    HomeScreenContent(
        title = context.getString(R.string.home_title_login),
        onLogoutClick = { viewModel.onButtonLogoutClicked() }
    )
}
