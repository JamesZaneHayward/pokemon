package com.james.hayward.pokemon.domain

import com.james.hayward.pokemon.data.PokeApiService
import com.james.hayward.pokemon.data.model.Pokemon
import com.james.hayward.pokemon.data.model.PokemonList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@ExtendWith(MockKExtension::class)
class GetWhoIsThatPokemonInfoUseCaseTest {

    @MockK
    private lateinit var service: PokeApiService

    @InjectMockKs
    private lateinit var sut: GetWhoIsThatPokemonInfoUseCase

    @Test
    fun `given getPokemon returns empty body when execute is called then throw IllegalStateException`() {
        coEvery { service.getPokemon(151) } returns mockk<Response<PokemonList>>().apply {
            every { this@apply.body() } returns null
        }

        runTest {
            assertThrows<IllegalStateException> {
                sut.execute()
            }
        }
    }

    @Test
    fun `given getPokemon returns list when execute is called then getPokemonByIdentifier is called`() {
        getPokemonReturnsList()
        coEvery { service.getPokemonByIdentifier(any()) } returns mockk<Response<Pokemon>>().apply {
            every { this@apply.body() } returns mockk()
        }

        runTest {
            sut.execute()
        }

        coVerify(exactly = 1) {
            service.getPokemonByIdentifier(any())
        }
    }

    @Test
    fun `given getPokemon returns list and getPokemonByIdentifier returns empty body when execute is called then throw IllegalStateException`() {
        getPokemonReturnsList()
        coEvery { service.getPokemonByIdentifier(any()) } returns mockk<Response<Pokemon>>().apply {
            every { this@apply.body() } returns null
        }

        runTest {
            assertThrows<IllegalStateException> {
                sut.execute()
            }
        }
    }

    @Test
    fun `given getPokemon returns list and getPokemonByIdentifier returns Pokemon then return pair of pokemon and list`() {
        val pokemon = mockk<Pokemon>()
        getPokemonReturnsList()

        coEvery { service.getPokemonByIdentifier(any()) } returns mockk<Response<Pokemon>>().apply {
            every { this@apply.body() } returns pokemon
        }

        runTest {
            val result = sut.execute()
            assertEquals(pokemon, result.first)
            assertTrue(
                result.second.containsAll(
                    listOf(
                        NAME_ONE,
                        NAME_TWO,
                        NAME_THREE,
                        NAME_FOUR,
                    )
                )
            )
        }
    }

    private fun getPokemonReturnsList() {
        coEvery { service.getPokemon(151) } returns mockk<Response<PokemonList>>().apply {
            every { this@apply.body() } returns PokemonList(
                count = 4,
                next = "",
                results = listOf(
                    PokemonList.PokemonListItem(
                        name = NAME_ONE,
                        url = "",
                    ),
                    PokemonList.PokemonListItem(
                        name = NAME_TWO,
                        url = "",
                    ),
                    PokemonList.PokemonListItem(
                        name = NAME_THREE,
                        url = "",
                    ),
                    PokemonList.PokemonListItem(
                        name = NAME_FOUR,
                        url = "",
                    ),
                )
            )
        }
    }
}

private const val NAME_ONE = "name_one"
private const val NAME_TWO = "name_two"
private const val NAME_THREE = "name_three"
private const val NAME_FOUR = "name_four"
