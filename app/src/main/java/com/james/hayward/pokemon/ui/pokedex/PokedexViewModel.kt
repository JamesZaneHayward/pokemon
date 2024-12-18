package com.james.hayward.pokemon.ui.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.james.hayward.pokemon.data.common.ApiResponse
import com.james.hayward.pokemon.data.model.Pokemon
import com.james.hayward.pokemon.data.model.PokemonList
import com.james.hayward.pokemon.domain.GetPokedexUseCase
import com.james.hayward.pokemon.domain.GetPokemonDetailsUseCase
import com.james.hayward.pokemon.ui.pokedex.PokedexViewModel.PokedexListState.PokedexList
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
            val pokemon = getPokemonDetailsUseCase.execute(identifier)
            if (pokemon is ApiResponse.Success) {
                _viewState.update {
                    _viewState.value.copy(
                        selectedPokemon = pokemon.response
                    )
                }
            }
        }
    }

    fun showPokedexList() {
        viewModelScope.launch {
            _viewState.update {
                _viewState.value.copy(
                    pokedexList = when (val result = getPokedexUseCase.execute()) {
                        is ApiResponse.Success -> {
                            PokedexList(result.response)
                        }
                        is ApiResponse.Error,
                        is ApiResponse.Exception -> PokedexListState.NoListAvailable
                    }
                )
            }
        }
    }

    data class PokedexViewState(
        val pokedexList: PokedexListState = PokedexList(
            PokemonList(
                count = 0,
                next = "",
                results = emptyList(),
            ),
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

    sealed class PokedexListState {
        data class PokedexList(val pokemonList: PokemonList) : PokedexListState()
        object NoListAvailable : PokedexListState()
    }
}