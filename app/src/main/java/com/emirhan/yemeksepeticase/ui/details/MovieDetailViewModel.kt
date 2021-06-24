package com.emirhan.yemeksepeticase.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhan.yemeksepeticase.data.MovieDetails
import com.emirhan.yemeksepeticase.remote.MovieRepository
import com.emirhan.yemeksepeticase.utils.Events
import com.emirhan.yemeksepeticase.utils.Result
import com.emirhan.yemeksepeticase.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    val movieDetails = MutableLiveData<Events<Result<MovieDetails>>>()

    fun getMovieDetails(movieId: Int) =
        viewModelScope.launch {
            movieDetails.postValue(Events(Result(Status.LOADING, null)))
            movieDetails.postValue(Events(repository.getMovieDetails(movieId)))
        }
}