package com.james.hayward.pokemon.ui.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.james.hayward.pokemon.data.model.Pokemon
import com.james.hayward.pokemon.data.model.PokemonList
import com.james.hayward.pokemon.domain.GetPokedexUseCase
import com.james.hayward.pokemon.domain.GetPokemonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val getPokedexUseCase: GetPokedexUseCase,
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(PokedexViewState())
    val viewState: StateFlow<PokedexViewState> = _viewState.asStateFlow()

    init {
        showPokedexList()
    }

    fun getPokemonDetails(identifier: String) {
        viewModelScope.launch {
            _viewState.update {
                _viewState.value.copy(
                    selectedPokemon = getPokemonDetailsUseCase.execute(identifier)
                )
            }
        }
    }

    private fun showPokedexList() {
        viewModelScope.launch {
            _viewState.update {
                _viewState.value.copy(
                    pokedexList = getPokedexUseCase.execute()
                )
            }
        }
    }

    data class PokedexViewState(
        val pokedexList: PokemonList = PokemonList(
            count = 0,
            next = "",
            results = emptyList(),
        ),
        val selectedPokemon: Pokemon = Pokemon(
            id = 1,
            name = "bulbasaur",
            baseExp = 32,
            height = 0.7,
            weight = 6.9,
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
        ),
    )
}