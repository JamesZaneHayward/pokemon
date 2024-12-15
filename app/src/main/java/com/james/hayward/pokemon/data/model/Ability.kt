package com.james.hayward.pokemon.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Ability(
    val id: Int,
    val name: String,
    val names: List<Name>,
    @Json(name = "effect_entries") val effectEntries: List<VerboseEffect>,
    @Json(name = "flavor_text_entries") val flavourTextEntries: List<AbilityFlavorText>,
) {

    @Keep
    @JsonClass(generateAdapter = true)
    data class Name(
        val name: String,
        val language: Language,
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class VerboseEffect(
        val effect: String,
        @Json(name = "short_effect") val shortEffect: String,
        val language: Language,
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class AbilityFlavorText(
        @Json(name = "flavor_text") val flavourText: String,
        val language: Language,
    )
}
