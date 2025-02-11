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
import br.leeloo.fourauth.R
import br.leeloo.fourauth.presentation.action.RegisterAction
import br.leeloo.fourauth.presentation.state.RegisterState
import br.leeloo.fourauth.presentation.utils.ShowToast
import br.leeloo.fourauth.presentation.viewmodel.RegisterViewModel


@Composable
internal fun RegisterScreen(
    viewModel: RegisterViewModel,
    navController: NavHostController,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    RegisterScreenContent(
        state = state,
        onPasswordChanged = { viewModel.onPasswordChanged(it) },
        onEmailChanged = { viewModel.onEmailChanged(it) },
        onRegisterClicked = { viewModel.onButtonRegisterClicked() },
    )

    LaunchedEffect(viewModel) {
        viewModel.action.collect { action ->
            when (action) {
                is RegisterAction.RegisterButtonClicked -> viewModel.register()
                is RegisterAction.NavigateToHome -> {
                    navController.navigate(context.getString(R.string.nav_home)) {
                        popUpTo(context.getString(R.string.nav_register)) { inclusive = true }
                    }
                }
            }
        }
    }
    if (state.value.isSuccess) {
        ShowToast(context.getString(R.string.register_success))
    }
}

@Composable
private fun RegisterScreenContent(
    state: State<RegisterState>,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRegisterClicked: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_android),
            contentDescription = context.getString(R.string.register_logo),
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(60.dp))

        OutlinedTextField(
            value = state.value.email,
            onValueChange = onEmailChanged,
            label = { Text(context.getString(R.string.register_label_email)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = context.getString(R.string.register_content_description_icon)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.value.password,
            onValueChange = onPasswordChanged,
            label = { Text(context.getString(R.string.register_label_password)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = context.getString(R.string.register_content_description_icon_senha)
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRegisterClicked,
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.value.isLoading
        ) {
            Text(context.getString(R.string.register_button))
        }

        if (state.value.error != null) {
            Text(text = state.value.error!!, color = MaterialTheme.colorScheme.error)
        }

        if (state.value.isLoading) {
            CircularProgressIndicator()
        }
    }
}