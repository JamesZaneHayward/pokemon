package com.james.hayward.pokemon

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.james.hayward.pokemon.data.model.Pokemon
import com.james.hayward.pokemon.ui.theme.PokemonTheme
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsGame
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsViewModel
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsViewModel.GameState.GameData
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
                    gameData = GameData(
                        correctPokemon = Pokemon(
                            id = 1,
                            name = "Bulbasaur",
                            baseExp = 32,
                            height = 0.7,
                            weight = 6.7,
                            abilities = emptyList(),
                            types = listOf(
                                Pokemon.PokemonType(
                                    0,
                                    Pokemon.PokemonType.TypeShort(
                                        name = "Grass",
                                        url = "",
                                    )
                                ),
                                Pokemon.PokemonType(
                                    1,
                                    Pokemon.PokemonType.TypeShort(
                                        name = "Poison",
                                        url = "",
                                    )
                                ),
                            ),
                        ),
                        currentPokemonImageUrl = "",
                        pokemonChoices = listOf(
                            "Bulbasaur",
                            "Arbok",
                            "Eevee",
                            "Raichu",
                        ),
                    ),
                    hasGuessed = false,
                    showHint = false,
                    correct = 0,
                    incorrect = 0,
                    onPokemonSelected = { /*noop*/ },
                    onRevealHintClicked = { /*noop*/ },
                    onRefreshGameClicked = { /*noop*/ },
                    innerPadding = PaddingValues(0.dp)
                )
            }
        }

        composeTestRule.onNodeWithText("Bulbasaur").assertIsDisplayed().assertIsEnabled()
        composeTestRule.onNodeWithText("Arbok").assertIsDisplayed().assertIsEnabled()
        composeTestRule.onNodeWithText("Eevee").assertIsDisplayed().assertIsEnabled()
        composeTestRule.onNodeWithText("Raichu").assertIsDisplayed().assertIsEnabled()
    }
}
