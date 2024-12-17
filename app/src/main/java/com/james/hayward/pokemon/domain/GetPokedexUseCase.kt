package com.james.hayward.pokemon.domain

import com.james.hayward.pokemon.data.PokeApiService
import com.james.hayward.pokemon.data.model.PokemonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPokedexUseCase @Inject constructor(
    private val pokeApiService: PokeApiService,
) {
    suspend fun execute(pokemonCount: Int = 151): PokemonList {
        return withContext(Dispatchers.IO) {
            // sticking to first gen for now
            pokeApiService.getPokemon(pokemonCount).body() ?: throw IllegalStateException("Missing Body")
        }
    }
}