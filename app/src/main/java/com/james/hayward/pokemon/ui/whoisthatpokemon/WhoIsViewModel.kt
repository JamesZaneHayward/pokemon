package com.james.hayward.pokemon.ui.whoisthatpokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.james.hayward.pokemon.data.model.Pokemon
import com.james.hayward.pokemon.data.model.getFullArtUrl
import com.james.hayward.pokemon.domain.GetWhoIsThatPokemonInfoUseCase
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsViewModel.GameState.GameData
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsViewModel.GameState.Loading
import com.james.hayward.pokemon.ui.whoisthatpokemon.WhoIsViewModel.GameState.NoGameData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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
        val guessedCorrectly =
            selectedOption == (_viewState.value.gameState as GameData).correctPokemon.name

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
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                _viewState.update {
                    it.copy(
                        gameState = NoGameData,
                        hasGuessed = false,
                        showHint = false,
                    )
                }
            }
        ) {
            val gameInfo = getWhoIsThatPokemonInfoUseCase.execute()

            _viewState.update {
                it.copy(
                    gameState = GameData(
                        currentPokemonImageUrl = gameInfo.first.getFullArtUrl(),
                        correctPokemon = gameInfo.first,
                        pokemonChoices = gameInfo.second,
                    ),
                    hasGuessed = false,
                    showHint = false,
                )
            }
        }
    }

    data class WhoIsGameViewState(
        val gameState: GameState = Loading,
        val hasGuessed: Boolean = false,
        val showHint: Boolean = false,
        val correct: Int = 0,
        val incorrect: Int = 0,
    )

    sealed class GameState() {
        object Loading : GameState()
        object NoGameData : GameState()
        data class GameData(
            val currentPokemonImageUrl: String,
            val correctPokemon: Pokemon,
            val pokemonChoices: List<String>,
        ) : GameState()
    }
}
