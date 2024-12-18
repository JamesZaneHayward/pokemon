package com.james.hayward.pokemon.domain

import com.james.hayward.pokemon.data.PokemonRepository
import com.james.hayward.pokemon.data.common.ApiResponse
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
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetPokemonDetailsUseCaseTest {

    @MockK
    private lateinit var repository: PokemonRepository

    @InjectMockKs
    private lateinit var sut: GetPokemonDetailsUseCase

    @BeforeEach
    fun setup() {
        coEvery { repository.getPokemonByIdentifier(IDENTIFIER) } returns mockk<ApiResponse.Success<Pokemon>>().apply {
            every { this@apply.response } returns pokemon
            every { this@apply.code } returns HTTP_CODE_200
        }
    }

    @Test
    fun `given an identifier when execute is called then call getPokemonByIdentifier with that identifier`() {
        runTest {
            sut.execute(IDENTIFIER)
        }

        coVerify(exactly = 1) { repository.getPokemonByIdentifier(IDENTIFIER) }
    }

    @Test
    fun `given getPokemonByIdentifier returns success when execute is called then return pokemon`() {
        runTest {
            val result = sut.execute(IDENTIFIER)
            assertEquals(pokemon.name, (result as ApiResponse.Success).response.name)
            assertEquals(pokemon.id, result.response.id)
            assertEquals(pokemon.types, result.response.types)
            assertEquals(pokemon.baseExp, result.response.baseExp)
            assertEquals(pokemon.abilities, result.response.abilities)
            assertEquals(pokemon.height, result.response.height)
            assertEquals(pokemon.weight, result.response.weight)
        }
    }

    @Test
    fun `given getPokemonByIdentifier returns Error then return the error`() {
        val expectedError = mockk<ApiResponse.Error<Pokemon>>()
        coEvery { repository.getPokemonByIdentifier(IDENTIFIER) } returns expectedError

        runTest {
            assertEquals(expectedError, sut.execute(IDENTIFIER))
        }
    }

    @Test
    fun `given getPokemonByIdentifier returns Exception then return the error`() {
        val expectedException = mockk<ApiResponse.Exception<Pokemon>>()
        coEvery { repository.getPokemonByIdentifier(IDENTIFIER) } returns expectedException

        runTest {
            assertEquals(expectedException, sut.execute(IDENTIFIER))
        }
    }

    private val pokemon = Pokemon(
        id = 1,
        name = "bulbasaur",
        baseExp = 32,
        height = HEIGHT_IN_METRES,
        weight = WEIGHT_IN_KILOGRAMS,
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
private const val HEIGHT_IN_METRES = 108.4
private const val WEIGHT_IN_KILOGRAMS = 43.2
private const val HTTP_CODE_200 = 200
