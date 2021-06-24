package com.emirhan.yemeksepeticase.remote

import com.emirhan.yemeksepeticase.BuildConfig
import com.emirhan.yemeksepeticase.data.MovieDetails
import com.emirhan.yemeksepeticase.data.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {
    @GET("search/movie")
    suspend fun getMovies(
        @Query("query") search: String,
        @Query("page") pageNumber: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieDetails>
}