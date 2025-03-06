package br.leeloo.fourauth.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.leeloo.fourauth.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class HomeContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun myTest() {

             // Executa o clique
        composeTestRule.onNodeWithText("sair").performClick()
        // Verifica se o texto é exibido
        composeTestRule.onNodeWithText("HOME").assertIsDisplayed()
    }
}
