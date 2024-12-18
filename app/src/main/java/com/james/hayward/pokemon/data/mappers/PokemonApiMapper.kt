package com.james.hayward.pokemon.data.mappers

import com.james.hayward.pokemon.data.model.Pokemon

/**
 * In a real situation we would either request the api returns data in the correct format
 * OR we would map from a Data model to a Domain model but for the sake of convenience
 * and given we're using an unrelated api we're just going to map to and from the same
 * model Pokemon with the correct value for what we want.
 */
object PokemonApiMapper : Mapper<Pokemon, Pokemon> {
    override fun toDomain(value: Pokemon): Pokemon {
        return value.copy(
            // API is returning height in decimetres - converting to metres
            height = value.height / 10,
            // API is returning weight in hectograms - converting to kilograms
            weight = value.weight / 10,
        )
    }
}
