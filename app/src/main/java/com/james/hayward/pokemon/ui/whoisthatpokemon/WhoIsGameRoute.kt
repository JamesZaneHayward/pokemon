package com.james.hayward.pokemon.ui.whoisthatpokemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.james.hayward.pokemon.ui.theme.Typography
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsViewModel.GameState.GameData
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsViewModel.GameState.NoGameData
import java.util.Locale

@Composable
fun WhoIsGameRoute(
    viewModel: WhoIsViewModel,
    innerPadding: PaddingValues,
) {
    val whoIsGameViewState by viewModel.viewState.collectAsStateWithLifecycle()
    when (whoIsGameViewState.gameState) {
        is GameData -> {
            WhoIsGame(
                hasGuessed = whoIsGameViewState.hasGuessed,
                showHint = whoIsGameViewState.showHint,
                correct = whoIsGameViewState.correct,
                incorrect = whoIsGameViewState.incorrect,
                gameData = whoIsGameViewState.gameState as GameData,
                onPokemonSelected = { viewModel.onPokemonSelected(it) },
                onRevealHintClicked = { viewModel.revealHint() },
                onRefreshGameClicked = { viewModel.onRefreshGameClicked() },
                innerPadding = innerPadding,
            )
        }

        WhoIsViewModel.GameState.Loading -> {
            Column(
                modifier = Modifier.padding(innerPadding)
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }

        NoGameData -> {
            Column(
                modifier = Modifier.padding(innerPadding)
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "No game data available, please try fetching data again")
                Button(onClick = { viewModel.onRefreshGameClicked() }) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

@Composable
fun OptionButton(
    onClickAction: () -> Unit,
    pokemonName: String,
    isCorrect: Boolean,
    hasGuessed: Boolean,
) {
    Button(
        onClick = onClickAction,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .size(width = 160.dp, height = 60.dp),
        colors = ButtonColors(
            containerColor = Color.Gray,
            contentColor = Color.White,
            disabledContainerColor = if (isCorrect) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary,
            disabledContentColor = if (isCorrect) Color.Black else Color.White,
        ),
        enabled = hasGuessed.not(),
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            style = Typography.bodyLarge,
            text = pokemonName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            },
        )
    }
}

@Composable
fun WhoIsGame(
    gameData: GameData,
    hasGuessed: Boolean,
    showHint: Boolean,
    correct: Int,
    incorrect: Int,
    onPokemonSelected: (String) -> Unit,
    onRevealHintClicked: () -> Unit,
    onRefreshGameClicked: () -> Unit,
    innerPadding: PaddingValues,
) {
    Column(
        modifier = Modifier.padding(innerPadding)
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 8.dp)
                .size(384.dp),
            model = gameData.currentPokemonImageUrl,
            contentDescription = "Pokémon silhouette",
            colorFilter = if (hasGuessed.not()) ColorFilter.tint(Color.Black) else null
        )
        // first line of pokemon name buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            gameData.pokemonChoices[0].let {
                OptionButton(
                    onClickAction = { onPokemonSelected(it) },
                    pokemonName = it,
                    isCorrect = gameData.correctPokemon.name == it,
                    hasGuessed = hasGuessed,
                )
            }
            gameData.pokemonChoices[1].let {
                OptionButton(
                    onClickAction = { onPokemonSelected(it) },
                    pokemonName = it,
                    isCorrect = gameData.correctPokemon.name == it,
                    hasGuessed = hasGuessed,
                )
            }
        }
        // second line of pokemon name buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            gameData.pokemonChoices[2].let {
                OptionButton(
                    onClickAction = { onPokemonSelected(it) },
                    pokemonName = it,
                    isCorrect = gameData.correctPokemon.name == it,
                    hasGuessed = hasGuessed,
                )
            }
            gameData.pokemonChoices[3].let {
                OptionButton(
                    onClickAction = { onPokemonSelected(it) },
                    pokemonName = it,
                    isCorrect = gameData.correctPokemon.name == it,
                    hasGuessed = hasGuessed,
                )
            }
        }
        if (showHint) {
            gameData.correctPokemon.types.first().type.name.let {
                Text(
                    modifier = Modifier
                        .height(72.dp)
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Pokémon type: ${
                        it.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                        }
                    }",
                )
            }
        } else {
            Button(
                modifier = Modifier
                    .height(72.dp)
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { onRevealHintClicked() },
                colors = ButtonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Black
                ),
            ) {
                Text(
                    text = "HINT",
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Correct guesses: $correct",
            )
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = "Incorrect guesses: $incorrect",
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End),
            onClick = { onRefreshGameClicked() },
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.Black
            ),
        ) {
            Text(
                text = "New Game"
            )
        }
    }
}
