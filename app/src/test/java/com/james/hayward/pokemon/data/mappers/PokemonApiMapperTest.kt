package com.james.hayward.pokemon.data.mappers

import com.james.hayward.pokemon.data.model.Pokemon
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PokemonApiMapperTest {
    val sut = PokemonApiMapper

    @Test
    fun `given a HEIGHT_IN_DECIMETRES when toDomain is called then return pokemon with height in metres`() {
        assertEquals(
            HEIGHT_IN_METRES,
            sut.toDomain(pokemon).height
        )
    }

    @Test
    fun `given a WEIGHT_IN_DECIMETRES when toDomain is called then return pokemon with weight in kilograms`() {
        assertEquals(
            WEIGHT_IN_KILOGRAMS,
            sut.toDomain(pokemon).weight
        )
    }

    private val pokemon = Pokemon(
        id = 1,
        name = "bulbasaur",
        baseExp = 32,
        height = HEIGHT_IN_DECIMETRES,
        weight = WEIGHT_IN_HECTOGRAMS,
        abilities = emptyList(),
        types = listOf(
            Pokemon.PokemonType(
                0,
                Pokemon.PokemonType.TypeShort(
                    name = "Grass",
                    url = "",
                )
            ),
            Pokemon.PokemonType(
                1,
                Pokemon.PokemonType.TypeShort(
                    name = "Poison",
                    url = "",
                )
            ),
        ),
    )
}

private const val HEIGHT_IN_DECIMETRES = 1084.0
private const val HEIGHT_IN_METRES = 108.4
private const val WEIGHT_IN_HECTOGRAMS = 432.0
private const val WEIGHT_IN_KILOGRAMS = 43.2
