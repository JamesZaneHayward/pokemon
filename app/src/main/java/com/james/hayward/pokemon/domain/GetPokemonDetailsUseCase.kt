package com.james.hayward.pokemon.domain

import com.james.hayward.pokemon.data.PokeApiService
import com.james.hayward.pokemon.data.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject constructor(
    private val pokeApiService: PokeApiService,
) {
    suspend fun execute(identifier: String): Pokemon {
        return withContext(Dispatchers.IO) {
            val pokemonTemp = pokeApiService.getPokemonByIdentifier(identifier).body()
                ?: throw IllegalStateException("Missing Body")

            pokemonTemp.copy(
                // API is returning height in decimetres - converting to metres
                height = pokemonTemp.height / 10,
                // API is returning weight in hectograms - converting to kilograms
                weight = pokemonTemp.weight / 10,
            )
        }
    }
}