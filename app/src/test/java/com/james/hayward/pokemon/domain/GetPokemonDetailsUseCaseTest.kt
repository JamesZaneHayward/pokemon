package com.james.hayward.pokemon.domain

import com.james.hayward.pokemon.data.PokeApiService
import com.james.hayward.pokemon.data.model.Pokemon
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response
import java.lang.IllegalStateException

@ExtendWith(MockKExtension::class)
class GetPokemonDetailsUseCaseTest {

    @MockK
    private lateinit var service: PokeApiService

    @InjectMockKs
    private lateinit var sut: GetPokemonDetailsUseCase

    @BeforeEach
    fun setup() {
        coEvery { service.getPokemonByIdentifier(IDENTIFIER) } returns mockk<Response<Pokemon>>().apply {
            every { this@apply.body() } returns pokemon
        }
    }

    @Test
    fun `given getPokemonByIdentifier returns empty body when execute is called then throw IllegalStateException`() {
        coEvery { service.getPokemonByIdentifier(IDENTIFIER) } returns mockk<Response<Pokemon>>().apply {
            every { this@apply.body() } returns null
        }

        runTest {
            assertThrows<IllegalStateException> {
                sut.execute(IDENTIFIER)
            }
        }
    }

    @Test
    fun `given an identifier when execute is called then call getPokemonByIdentifier with that identifier`() {
        runTest {
            sut.execute(IDENTIFIER)
        }

        coVerify(exactly = 1) { service.getPokemonByIdentifier(IDENTIFIER) }
    }

    @Test
    fun `given getPokemonByIdentifier returns pokemon when execute is called then return pokemon with height in metres`() {
        runTest {
            assertEquals(HEIGHT_IN_METRES, sut.execute(IDENTIFIER).height)
        }
    }

    @Test
    fun `given getPokemonByIdentifier returns pokemon when execute is called then return pokemon with weight in kilograms`() {
        runTest {
            assertEquals(WEIGHT_IN_KILOGRAMS, sut.execute(IDENTIFIER).weight)
        }
    }

    @Test
    fun `given getPokemonByIdentifier returns pokemon when execute is called then return pokemon`() {
        runTest {
            val result = sut.execute(IDENTIFIER)
            assertEquals(pokemon.name, result.name)
            assertEquals(pokemon.id, result.id)
            assertEquals(pokemon.types, result.types)
            assertEquals(pokemon.baseExp, result.baseExp)
            assertEquals(pokemon.abilities, result.abilities)
        }
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

private const val IDENTIFIER = "identifier"
private const val HEIGHT_IN_DECIMETRES = 1084.0
private const val HEIGHT_IN_METRES = 108.4
private const val WEIGHT_IN_HECTOGRAMS = 432.0
private const val WEIGHT_IN_KILOGRAMS = 43.2
