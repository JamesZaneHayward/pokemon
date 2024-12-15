package com.james.hayward.pokemon.domain

import com.james.hayward.pokemon.data.PokeApiService
import com.james.hayward.pokemon.data.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWhoIsThatPokemonInfoUseCase @Inject constructor(
    private val pokeApiService: PokeApiService,
) {
    suspend fun execute(): Pair<Pokemon, List<String>> {
        return withContext(Dispatchers.IO) {
            // sticking to first gen for now
            val pokemonList = pokeApiService.getPokemon(151).body()?.results
                // TODO add proper error handling
                ?: throw IllegalStateException("Missing Body")
            val randomPokemon = pokemonList.shuffled().take(4)

            val selectedPokemon =
                pokeApiService.getPokemonByIdentifier(randomPokemon[3].name).body()
                    ?: throw IllegalStateException("Missing Body")

            Pair(selectedPokemon, randomPokemon.map { it.name }.shuffled())
        }
    }
}