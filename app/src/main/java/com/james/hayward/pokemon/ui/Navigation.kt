package com.james.hayward.pokemon.ui

import androidx.navigation.NavController
import com.james.hayward.pokemon.ui.Destinations.POKEDEX_ROUTE
import com.james.hayward.pokemon.ui.Destinations.WHO_IS_GAME_ROUTE

object Destinations {
    const val WHO_IS_GAME_ROUTE = "whois"
    const val POKEDEX_ROUTE = "pokedex"
}

class NavigationActions(navController: NavController) {
    val navigateToWhoIsGame: () -> Unit = {
        navController.navigate(WHO_IS_GAME_ROUTE) {
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToPokedex: () -> Unit = {
        navController.navigate(POKEDEX_ROUTE) {
            launchSingleTop = true
            restoreState = true
        }
    }
}
