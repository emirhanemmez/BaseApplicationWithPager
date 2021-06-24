package com.emirhan.yemeksepeticase.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.emirhan.yemeksepeticase.data.MovieResponse
import com.emirhan.yemeksepeticase.remote.MovieRepository
import com.emirhan.yemeksepeticase.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val query = MutableLiveData("")
    val list = query.switchMap { query ->
        Pager(PagingConfig(pageSize = 12)) {
            MoviePaging(query, repository)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setQuery(search: String) {
        query.postValue(search)
    }

    val movieListData = MutableLiveData<Result<MovieResponse>>()

    fun getMovies(query: String, pageNumber: Int) = viewModelScope.launch {
        movieListData.postValue(repository.getMovies(query, pageNumber))
    }
}