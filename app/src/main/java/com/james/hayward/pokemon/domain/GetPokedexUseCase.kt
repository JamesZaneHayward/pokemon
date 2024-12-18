package com.james.hayward.pokemon.domain

import com.james.hayward.pokemon.data.PokemonRepository
import com.james.hayward.pokemon.data.common.ApiResponse
import com.james.hayward.pokemon.data.model.PokemonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPokedexUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {
    suspend fun execute(pokemonCount: Int = 151 /*Default to gen 1*/): ApiResponse<PokemonList> {
        return withContext(Dispatchers.IO) {
            pokemonRepository.getPokemon(pokemonCount)
        }
    }
}