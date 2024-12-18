package com.james.hayward.pokemon.domain

import com.james.hayward.pokemon.data.PokemonRepository
import com.james.hayward.pokemon.data.common.ApiResponse
import com.james.hayward.pokemon.data.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {
    suspend fun execute(identifier: String): ApiResponse<Pokemon> {
        return withContext(Dispatchers.IO) {
            pokemonRepository.getPokemonByIdentifier(identifier)
        }
    }
}