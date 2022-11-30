package com.example.movieapp.ui.movielist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Favorites
import com.example.movieapp.data.model.MoviesResponse
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
class MovieListViewModel @Inject constructor(var mrepo: MoviesRepository) : ViewModel() {

    private val _viewState = MutableStateFlow(MovieListViewState())
    val viewState: StateFlow<MovieListViewState> = _viewState.asStateFlow()
    val favoritesList = MutableLiveData<List<Favorites>>()
    var searchQuery = ""
    var pageNumber = 1
    var dataChanged = false

    init {
        _viewState.update {
            it.copy(
                isLoading = false
            )
        }
    }

    fun searchMovies(){
        viewModelScope.launch {
            dataChanged = true
            val response = mrepo.searchMovies(searchQuery, pageNumber)

            if(response.response == "False"){
                addErrorToList(response.error)
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        movieResponse = null
                    )
                }
            }else{
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        pageNumber = pageNumber,
                        movieResponse = response
                    )
                }
            }
        }
    }

    fun loadNextPage(){
        viewModelScope.launch{
            pageNumber++
            searchMovies()
        }
    }

    fun searchButton(){
        viewModelScope.launch{
            pageNumber = 1
            searchMovies()
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

    fun returnFavorites(){
        viewModelScope.launch {
            favoritesList.value = mrepo.returnFavorites()
        }
    }

    private fun addErrorToList(exception: String?) {
        exception?.let {
            val errorList =
                _viewState.value.consumableErrors?.toMutableList() ?: mutableListOf()
            errorList.add(ConsumableError(exception = it))
            _viewState.value =
                viewState.value.copy(
                    consumableErrors = errorList,
                    isLoading = false
                )
        }
    }

    fun errorConsumed(errorId: Long) {
        _viewState.update { currentUiState ->
            val newConsumableError =
                currentUiState.consumableErrors?.filterNot { it.id == errorId }
            currentUiState.copy(consumableErrors = newConsumableError, isLoading = false)
        }
    }
}

data class MovieListViewState(
    val isLoading: Boolean? = false,
    val pageNumber: Int? = 1,
    val movieResponse: MoviesResponse? = null,
    val consumableErrors: List<ConsumableError>? = null
)
