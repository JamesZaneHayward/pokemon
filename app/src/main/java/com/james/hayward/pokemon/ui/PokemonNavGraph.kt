package com.james.hayward.pokemon.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.james.hayward.pokemon.ui.pokedex.PokedexRoute
import com.james.hayward.pokemon.ui.pokedex.PokedexViewModel
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsGameRoute
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsViewModel

@Composable
fun PokemonNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.WHO_IS_GAME_ROUTE,
    innerPadding: PaddingValues,
) {
    Surface {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier,
        ) {
            composable(
                route = Destinations.WHO_IS_GAME_ROUTE,
            ) { backStackEntry ->
                val viewModel = hiltViewModel<WhoIsViewModel>()
                WhoIsGameRoute(
                    viewModel,
                    innerPadding,
                )
            }
            composable(
                route = Destinations.POKEDEX_ROUTE,
            ) { backStackEntry ->
                val viewModel = hiltViewModel<PokedexViewModel>()
                PokedexRoute(
                    viewModel,
                    innerPadding,
                )
            }
        }
    }
}
