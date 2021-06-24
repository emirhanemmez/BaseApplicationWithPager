package com.emirhan.yemeksepeticase.di

import com.emirhan.yemeksepeticase.BuildConfig
import com.emirhan.yemeksepeticase.remote.MovieInterface
import com.emirhan.yemeksepeticase.remote.MovieRepository
import com.emirhan.yemeksepeticase.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
object HiltModules {

    @Provides
    fun provideOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .addInterceptor(logger)
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
        }

    @Provides
    fun provideRetrofitInterface(okHttpClient: OkHttpClient): MovieInterface = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieInterface::class.java)

    @Provides
    fun provideRepository(movieInterface: MovieInterface): MovieRepository =
        MovieRepository(movieInterface)

}