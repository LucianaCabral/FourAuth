package br.leeloo.fourauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.leeloo.four.presentation.viewmodel.LoginViewModel
import br.leeloo.fourauth.presentation.screen.HomeScreen
import br.leeloo.fourauth.presentation.screen.LoginScreen
import br.leeloo.fourauth.presentation.screen.RegisterScreen
import br.leeloo.fourauth.presentation.viewmodel.HomeViewModel
import br.leeloo.fourauth.presentation.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginViewModel: LoginViewModel by viewModel()
        val registerViewModel: RegisterViewModel by viewModel()
        val homeViewModel: HomeViewModel by viewModel()

        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            MaterialTheme {
                Surface {

                    NavHost(navController = navController, startDestination = context.getString(R.string.nav_login)) {
                        composable(context.getString(R.string.nav_login)) {
                            LoginScreen(loginViewModel, navController)
                        }
                        composable(context.getString(R.string.nav_register)) {
                            RegisterScreen(registerViewModel, navController = navController)
                        }
                        composable(context.getString(R.string.nav_home)) {
                            HomeScreen(viewModel = homeViewModel, navController = navController)
                        }
                    }
                }
            }
        }
    }
}