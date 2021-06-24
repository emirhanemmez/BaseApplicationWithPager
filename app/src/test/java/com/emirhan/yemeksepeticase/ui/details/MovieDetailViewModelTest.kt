package com.emirhan.yemeksepeticase.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.emirhan.yemeksepeticase.remote.MovieRepository
import com.emirhan.yemeksepeticase.utils.Events
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
class MovieDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    private lateinit var viewModel: MovieDetailViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = MovieDetailViewModel(repository)
    }

    @Test
    fun `get movie detail by movie id`() {
        runBlocking {
            val observer = Mockito.mock(Observer::class.java) as Observer<Events<Result<com.emirhan.yemeksepeticase.data.MovieDetails>>>
            viewModel.movieDetails.observeForever(observer)
            val response = viewModel.getMovieDetails(1000)

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