package br.leeloo.fourauth.presentation.utils

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShowToast(string: String) {
    Toast.makeText(LocalContext.current, string, Toast.LENGTH_SHORT).show()
}
