package com.james.hayward.pokemon.data.mappers

interface Mapper<in InputModel, out OutputModel> {

    fun toDomain(value: InputModel): OutputModel
}