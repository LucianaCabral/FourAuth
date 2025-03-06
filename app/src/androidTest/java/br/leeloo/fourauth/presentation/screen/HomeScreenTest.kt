package br.leeloo.fourauth.presentation.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import br.leeloo.fourauth.MainActivity
import br.leeloo.fourauth.presentation.viewmodel.HomeViewModel
import org.junit.Rule
import org.junit.Test

internal class HomeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController
    private lateinit var viewModel: HomeViewModel

    @Test
    fun myTest() {
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    navController = navController ,
                    viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("Sair").performClick()

        composeTestRule.onNodeWithText("HOME").assertIsDisplayed()
    }
}
