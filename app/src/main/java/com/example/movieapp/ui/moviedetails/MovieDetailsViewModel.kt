package com.example.movieapp.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.MovieDetails
import com.example.movieapp.data.repo.MoviesRepository
import com.example.movieapp.error.ConsumableError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(var mrepo: MoviesRepository) : ViewModel() {

    private val _viewState = MutableStateFlow(MovieDetailsViewState())
    val viewState: StateFlow<MovieDetailsViewState> = _viewState.asStateFlow()

    lateinit var movieID: String

    init {
        _viewState.update {
            it.copy(
                isLoading = true
            )
        }
        loadDetails()
    }

    private fun loadDetails(){
        viewModelScope.launch {
            _viewState.update {
                it.copy(
                    isLoading = false,
                    movie = mrepo.loadDetails(movieID)
                )
            }
        }
    }

    fun addToFavorites(movieID: String){
        viewModelScope.launch {
            mrepo.addToFavorites(movieID)
        }
    }

    fun removeFromFavorites(movieID: String){
        viewModelScope.launch {
            mrepo.removeFromFavorites(movieID)
        }
    }

    suspend fun checkFavorites(movieID: String): Boolean{
        return mrepo.checkFavorites(movieID)
    }

    fun errorConsumed(errorId: Long) {
        _viewState.update { currentUiState ->
            val newConsumableError =
                currentUiState.consumableErrors?.filterNot { it.id == errorId }
            currentUiState.copy(consumableErrors = newConsumableError, isLoading = false)
        }
    }
}

data class MovieDetailsViewState(
    val isLoading: Boolean? = false,
    val movie: MovieDetails? = null,
    val consumableErrors: List<ConsumableError>? = null
)