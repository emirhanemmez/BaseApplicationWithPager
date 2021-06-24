package com.emirhan.yemeksepeticase.ui.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.emirhan.yemeksepeticase.data.Movie
import com.emirhan.yemeksepeticase.remote.MovieRepository

class MoviePaging(private val search: String, private val movieRepository: MovieRepository) :
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {
            val response = movieRepository.getMovies(search, page)
            LoadResult.Page(
                data = response.data?.results ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.data?.results?.isNullOrEmpty()!!) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}