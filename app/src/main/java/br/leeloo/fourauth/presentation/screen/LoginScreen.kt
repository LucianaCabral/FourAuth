package br.leeloo.fourauth.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import br.leeloo.four.presentation.viewmodel.LoginViewModel
import br.leeloo.fourauth.R
import br.leeloo.fourauth.presentation.action.LoginAction
import br.leeloo.fourauth.presentation.state.LoginState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavHostController,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LoginScreenContent(
        state = state,
        onEmailChanged = { viewModel.onEmailChanged(it) },
        onPasswordChanged = { viewModel.onPasswordChanged(it) },
        onLoginClicked = { viewModel.onButtonLoginClicked() },
        onRegisterClicked = viewModel::onButtonRegisterClicked,
    )

    LaunchedEffect(viewModel) {
        viewModel.action.collectLatest { action ->
            when (action) {
                LoginAction.LoginButtonClicked -> viewModel.login()
                LoginAction.onResgisterClicked -> navController.navigate(context.getString(R.string.nav_register))
                LoginAction.NavigateToHome -> navController.navigate(context.getString(R.string.nav_home)) {
                    popUpTo(context.getString(R.string.nav_login)) { inclusive = true }
                }
            }
        }
    }
}

@Composable
private fun LoginScreenContent(
    state: State<LoginState>,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginLogo()
        Spacer(modifier = Modifier.height(24.dp))
        EmailTextField(state, onEmailChanged)
        Spacer(modifier = Modifier.height(8.dp))
        PasswordTextField(state, onPasswordChanged)
        Spacer(modifier = Modifier.height(16.dp))
        LoginButton(state, onLoginClicked)
        RegisterButton(onRegisterClicked)
        LoginError(state)
        LoginLoading(state)
    }
}

@Composable
private fun LoginLogo() {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.ic_android),
        contentDescription = context.getString(R.string.login_logo),
        modifier = Modifier.size(150.dp)
    )
}

@Composable
private fun EmailTextField(state: State<LoginState>, onEmailChanged: (String) -> Unit) {
    val context = LocalContext.current
    OutlinedTextField(
        value = state.value.email,
        onValueChange = onEmailChanged,
        label = { Text(context.getString(R.string.login_label_email)) },
        leadingIcon = {
            Icon(
                Icons.Default.Email, contentDescription = context
                    .getString(R.string.register_content_description_icon)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun PasswordTextField(state: State<LoginState>, onPasswordChanged: (String) -> Unit) {
    val context = LocalContext.current
    OutlinedTextField(
        value = state.value.password,
        onValueChange = onPasswordChanged,
        label = { Text(context.getString(R.string.login_label_password)) },
        leadingIcon = {
            Icon(
                Icons.Default.Lock, contentDescription = context
                    .getString(R.string.register_content_description_icon_senha)
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun LoginButton(state: State<LoginState>, onLoginClicked: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = onLoginClicked,
        modifier = Modifier.fillMaxWidth(),
        enabled = !state.value.isLoading
    ) {
        Text(context.getString(R.string.login_button))
    }
}

@Composable
private fun RegisterButton(onRegisterClicked: () -> Unit) {
    val context = LocalContext.current
    TextButton(onClick = onRegisterClicked) {
        Text(context.getString(R.string.login_button_register))
    }
}

@Composable
private fun LoginError(state: State<LoginState>) {
    if (state.value.error != null) {
        Text(text = state.value.error.orEmpty(), color = MaterialTheme.colorScheme.error)
    }
}

@Composable
private fun LoginLoading(state: State<LoginState>) {
    if (state.value.isLoading) {
        CircularProgressIndicator()
    }
}