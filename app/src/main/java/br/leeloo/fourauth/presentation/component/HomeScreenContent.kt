package br.leeloo.fourauth.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import br.leeloo.fourauth.R

@Composable
fun HomeScreenContent(
    title: String,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText(title = title)
        LogoutButton(onClick = onLogoutClick)
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        text = title,
        fontSize = 42.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    val context = LocalContext.current
    Button(onClick = onClick) {
        Text(context.getString(R.string.logout))
    }
}



