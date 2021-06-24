package com.emirhan.yemeksepeticase.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.emirhan.yemeksepeticase.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    private val viewModel: MovieViewModel by viewModels()
    private val moviePagingAdapter = MoviePagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerViewMovie.apply {
            adapter = moviePagingAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }

        binding.editTextSearchMovie.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                viewModel.setQuery(it.toString())
            } else
                moviePagingAdapter.submitData(lifecycle, PagingData.empty())
        }

        moviePagingAdapter.onMovieClick {
            val action = MovieFragmentDirections.actionMovieFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }

        viewModel.list.observe(viewLifecycleOwner) {
            moviePagingAdapter.submitData(lifecycle, it)
        }
    }
}