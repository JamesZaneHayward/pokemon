package com.james.hayward.pokemon

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.james.hayward.pokemon.ui.theme.PokemonTheme
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsGame
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsViewModel.WhoIsGameViewState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WhoIsGameUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testWhoIsGameSetup() {
        composeTestRule.setContent {
            PokemonTheme {
                WhoIsGame(
                    whoIsGameViewState = WhoIsGameViewState(
                        pokemonChoices = listOf(
                            "Charmander",
                            "Arbok",
                            "Eevee",
                            "Raichu",
                        ),
                    ),
                    onPokemonSelected = { /*noop*/ },
                    onRevealHintClicked = { /*noop*/ },
                    onRefreshGameClicked = { /*noop*/ },
                    innerPadding = PaddingValues(0.dp)
                )
            }
        }

        composeTestRule.onNodeWithText("Charmander").assertIsDisplayed().assertIsEnabled()
        composeTestRule.onNodeWithText("Arbok").assertIsDisplayed().assertIsEnabled()
        composeTestRule.onNodeWithText("Eevee").assertIsDisplayed().assertIsEnabled()
        composeTestRule.onNodeWithText("Raichu").assertIsDisplayed().assertIsEnabled()
    }
}
