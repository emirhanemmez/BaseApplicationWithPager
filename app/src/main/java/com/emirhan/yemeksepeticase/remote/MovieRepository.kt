package com.emirhan.yemeksepeticase.remote

import com.emirhan.yemeksepeticase.data.MovieDetails
import com.emirhan.yemeksepeticase.data.MovieResponse
import com.emirhan.yemeksepeticase.utils.Result
import com.emirhan.yemeksepeticase.utils.Status

open class MovieRepository(private val movieInterface: MovieInterface) {

    suspend fun getMovies(query: String, pageNumber: Int): Result<MovieResponse> {
        return try {
            val response = movieInterface.getMovies(query, pageNumber)
            if (response.isSuccessful) {
                Result(Status.SUCCESS, response.body())
            } else {
                Result(Status.ERROR, null)
            }
        } catch (e: Exception) {
            Result(Status.ERROR, null)
        }
    }

    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails> {
        return try {
            val response = movieInterface.getMovieDetails(movieId)
            if (response.isSuccessful) {
                Result(Status.SUCCESS, response.body())
            } else {
                Result(Status.ERROR, null)
            }
        } catch (e: Exception) {
            Result(Status.ERROR, null)
        }
    }

}