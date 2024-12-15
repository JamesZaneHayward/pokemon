package com.james.hayward.pokemon.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.james.hayward.pokemon.R
import com.james.hayward.pokemon.ui.Destinations.POKEDEX_ROUTE
import com.james.hayward.pokemon.ui.Destinations.WHO_IS_GAME_ROUTE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }

    var title by remember { mutableStateOf<String>("") }

    LaunchedEffect(navController.currentBackStackEntryFlow) {
        navController.currentBackStackEntryFlow.collect {
            title = when (it.destination.route) {
                WHO_IS_GAME_ROUTE -> "Who's that Pokémon?"
                POKEDEX_ROUTE -> "Pokédex"
                else -> ""
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary,
                ),
                title = {
                    Text(text = title)
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        modifier = Modifier.weight(0.5f),
                        onClick = { navigationActions.navigateToWhoIsGame() },
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_game),
                                contentDescription = "Navigate to Who's that Pokémon",
                                modifier = Modifier.size(64.dp),
                            )
                        }
                    }
                    IconButton(
                        modifier = Modifier.weight(0.5f),
                        onClick = { navigationActions.navigateToPokedex() },
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_pokedex),
                                contentDescription = "Navigate to Pokédex",
                                modifier = Modifier.size(64.dp),
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        PokemonNavGraph(
            navController = navController,
            innerPadding = innerPadding,
        )
    }
}
