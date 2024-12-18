package com.james.hayward.pokemon.data

import com.james.hayward.pokemon.data.common.ApiHandler
import com.james.hayward.pokemon.data.common.ApiResponse
import com.james.hayward.pokemon.data.mappers.PokemonApiMapper
import com.james.hayward.pokemon.data.model.Pokemon
import com.james.hayward.pokemon.data.model.PokemonList
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokeApiService: PokeApiService
) : ApiHandler {

    suspend fun getPokemonByIdentifier(identifier: String): ApiResponse<Pokemon> {
        return handleApiWithMapper(mapper = PokemonApiMapper) { pokeApiService.getPokemonByIdentifier(identifier) }
    }

    suspend fun getPokemon(limit: Int): ApiResponse<PokemonList> {
        return handleApi { pokeApiService.getPokemon(limit) }
    }
}
