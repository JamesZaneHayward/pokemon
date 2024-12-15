package com.james.hayward.pokemon.data

import com.james.hayward.pokemon.data.model.Pokemon
import com.james.hayward.pokemon.data.model.PokemonList
import com.james.hayward.pokemon.data.model.Type
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("api/v2/pokemon/{identifier}")
    suspend fun getPokemonByIdentifier(
        @Path("identifier") identifier: String,
    ): Response<Pokemon>

    @GET("api/v2/pokemon")
    suspend fun getPokemon(
        @Query("limit") limit: Int,
    ): Response<PokemonList>

    @GET("api/v2/pokemon/{typeId}")
    suspend fun getTypeInfo(
        @Path("typeId") typeId: String,
    ): Response<Type>
}
