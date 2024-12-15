package com.james.hayward.pokemon.ui.whoisthatpokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.james.hayward.pokemon.data.model.Pokemon
import com.james.hayward.pokemon.data.model.getFullArtUrl
import com.james.hayward.pokemon.domain.GetWhoIsThatPokemonInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WhoIsViewModel @Inject constructor(
    private val getWhoIsThatPokemonInfoUseCase: GetWhoIsThatPokemonInfoUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(WhoIsGameViewState())
    val viewState: StateFlow<WhoIsGameViewState> = _viewState.asStateFlow()

    init {
        generateWhoIsGame()
    }

    fun onRefreshGameClicked() {
        generateWhoIsGame()
    }

    fun onPokemonSelected(selectedOption: String) {
        val guessedCorrectly = selectedOption == _viewState.value.correctPokemon?.name
        _viewState.update {
            it.copy(
                hasGuessed = true,
                correct = it.correct + if (guessedCorrectly) 1 else 0,
                incorrect = it.incorrect + if (guessedCorrectly.not()) 1 else 0,
            )
        }
    }

    fun revealHint() {
        _viewState.update {
            it.copy(
                showHint = true
            )
        }
    }

    private fun generateWhoIsGame() {
        viewModelScope.launch {
            val gameInfo = getWhoIsThatPokemonInfoUseCase.execute()

            _viewState.update {
                it.copy(
                    currentPokemonImageUrl = gameInfo.first.getFullArtUrl(),
                    correctPokemon = gameInfo.first,
                    pokemonChoices = gameInfo.second,
                    hasGuessed = false,
                    showHint = false,
                    correct = it.correct,
                    incorrect = it.incorrect,
                )
            }
        }
    }

    data class WhoIsGameViewState(
        val currentPokemonImageUrl: String? = null,
        val correctPokemon: Pokemon? = null,
        val pokemonChoices: List<String>? = null,
        val hasGuessed: Boolean = false,
        val showHint: Boolean = false,
        val correct: Int = 0,
        val incorrect: Int = 0,
    )
}