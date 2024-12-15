package com.james.hayward.pokemon.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Type(
    val id: Int,
    val name: String,
    @Json(name = "damage_relations") val damageRelations: TypeRelations,
    val pokemon: List<TypePokemon>
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class TypeRelations(
        @Json(name = "no_damage_to") val noDamageTo: List<Type>,
        @Json(name = "half_damage_to") val halfDamageTo: List<Type>,
        @Json(name = "double_damage_to") val doubleDamageTo: List<Type>,
        @Json(name = "no_damage_from") val noDamageFrom: List<Type>,
        @Json(name = "half_damage_from") val halfDamageFrom: List<Type>,
        @Json(name = "double_damage_from") val doubleDamageFrom: List<Type>,
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class TypePokemon(
        val pokemon: Pokemon,
    )
}