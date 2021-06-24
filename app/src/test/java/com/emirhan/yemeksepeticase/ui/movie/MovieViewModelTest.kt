package com.emirhan.yemeksepeticase.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.emirhan.yemeksepeticase.data.MovieResponse
import com.emirhan.yemeksepeticase.remote.MovieRepository
import com.emirhan.yemeksepeticase.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ObsoleteCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    private lateinit var viewModel: MovieViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = MovieViewModel(repository)
    }

    @Test
    fun `get movie list by search query`() {
        runBlocking {
            val observer = Mockito.mock(Observer::class.java) as Observer<Result<MovieResponse>>
            viewModel.movieListData.observeForever(observer)
            val response = viewModel.getMovies("mask", 1)

            assertNotNull(response)
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun finish() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}