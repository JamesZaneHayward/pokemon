package com.james.hayward.pokemon.data.model

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class PokemonList(
    val count: Int,
    val next: String,
    val results: List<PokemonListItem>
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class PokemonListItem(
        val name: String,
        val url: String,
    )
}
