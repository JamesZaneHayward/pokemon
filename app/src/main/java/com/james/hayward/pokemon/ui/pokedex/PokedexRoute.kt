package com.james.hayward.pokemon.ui.pokedex

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.james.hayward.pokemon.data.model.Pokemon
import com.james.hayward.pokemon.data.model.getFullArtUrl
import com.james.hayward.pokemon.ui.pokedex.PokedexViewModel.PokedexViewState
import com.james.hayward.pokemon.ui.theme.Typography
import java.util.Locale

@Composable
fun PokedexRoute(
    viewModel: PokedexViewModel,
    innerPadding: PaddingValues,
) {
    val pokedexViewState by viewModel.viewState.collectAsStateWithLifecycle()
    Pokedex(
        pokedexViewState = pokedexViewState,
        selectPokemon = { viewModel.getPokemonDetails(it) },
        retryButtonClicked = { viewModel.showPokedexList() },
        innerPadding = innerPadding,
    )
}

@Composable
fun Pokedex(
    pokedexViewState: PokedexViewState,
    selectPokemon: (String) -> Unit,
    retryButtonClicked: () -> Unit,
    innerPadding: PaddingValues,
) {
    Column(
        modifier = Modifier.padding(innerPadding)
    ) {
        PokedexDetail(pokedexViewState.selectedPokemon)
        HorizontalDivider(
            color = Color.Black,
        )
        when (pokedexViewState.pokedexList) {
            is PokedexViewModel.PokedexListState.NoListAvailable -> {
                Text(text = "Oops, something went wrong, there's no list available, please try fetching data again")
                Button(onClick = retryButtonClicked) {
                    Text("Retry")
                }
            }
            is PokedexViewModel.PokedexListState.PokedexList -> {
                LazyColumn {
                    items(pokedexViewState.pokedexList.pokemonList.results) {
                        PokedexItem(it.name) { name -> selectPokemon(name) }
                    }
                }
            }
        }
    }
}

@Composable
fun PokedexDetail(selectedPokemon: Pokemon) {
    Column(
        Modifier.padding(bottom = 24.dp, start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Text(
            style = Typography.titleLarge,
            text = selectedPokemon.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            },
        )
        AsyncImage(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally)
                .size(384.dp),
            model = selectedPokemon.getFullArtUrl(),
            contentDescription = "Pokémon silhouette",
        )
        Text(text = "Pokédex Number: ${selectedPokemon.id}")
        Row {
            Text(modifier = Modifier.weight(0.5f), text = "Weight: ${selectedPokemon.weight} kg")
            Text(modifier = Modifier.weight(0.5f), text = "Height: ${selectedPokemon.height} m")
        }
        Row {
            selectedPokemon.types.forEach {
                Text(
                    modifier = Modifier.weight(0.5f),
                    text = "Type: ${it.type.name.capitalize()}",
                )
            }
        }
    }
}

@Composable
fun PokedexItem(name: String = "bulbasaur", onClickAction: (String) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClickAction(name) }
    ) {
        Text(
            modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp),
            text = name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        )
    }
}
