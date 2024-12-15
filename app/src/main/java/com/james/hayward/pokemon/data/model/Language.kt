package com.james.hayward.pokemon.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Keep
@JsonClass(generateAdapter = true)
data class Language(
    val id: Int,
    val name: String,
    @Json(name = "iso639") val countryCode: String, // two-letter country code where language is spoken - not unique
    @Json(name = "iso3166") val languageCode: String, // two-letter code of the language - not unique
)