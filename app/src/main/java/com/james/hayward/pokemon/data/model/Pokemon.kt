package com.james.hayward.pokemon.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Pokemon(
    val id: Int,
    val name: String,
    @Json(name = "base_experience") val baseExp: Int,
    val height: Double,
    val weight: Double,
    val abilities: List<PokemonAbility>,
    val types: List<PokemonType>,
) {

    /**
     * Ability per pokemon, including specific data such as hidden status for that pokemon
     */
    @Keep
    @JsonClass(generateAdapter = true)
    data class PokemonAbility(
        @Json(name = "is_hidden") val isHidden: Boolean,
        val slot: Int,
        val ability: AbilityShort,
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class AbilityShort(
            val name: String,
            val url: String,
        )
    }

    /**
     * Type per pokemon, including slot (which to show first)
     */
    @Keep
    @JsonClass(generateAdapter = true)
    data class PokemonType(
        val slot: Int,
        val type: TypeShort,
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class TypeShort(
            val name: String,
            val url: String,
        )
    }
}

// Extension functions

fun Pokemon.getSpriteUrl(): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
}

fun Pokemon.getFullArtUrl(): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
}
