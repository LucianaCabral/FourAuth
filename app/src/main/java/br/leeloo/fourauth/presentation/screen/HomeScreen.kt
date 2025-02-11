package br.leeloo.fourauth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.leeloo.fourauth.R
import br.leeloo.fourauth.presentation.action.HomeAction
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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = context.getString(R.string.home_title_login),
            fontSize = 42.sp,
            textAlign = TextAlign.Center
        )
        Button(onClick = { viewModel.logout() }) {
            Text(context.getString(R.string.logout))
        }
    }
}