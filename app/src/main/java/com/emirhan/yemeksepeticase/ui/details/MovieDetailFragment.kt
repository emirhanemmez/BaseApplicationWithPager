package com.emirhan.yemeksepeticase.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emirhan.yemeksepeticase.R
import com.emirhan.yemeksepeticase.data.Genre
import com.emirhan.yemeksepeticase.databinding.FragmentMovieDetailBinding
import com.emirhan.yemeksepeticase.utils.Status
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding

    private val args: MovieDetailFragmentArgs by navArgs()

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getMovieDetails(args.movieId)

        viewModel.movieDetails.observe(viewLifecycleOwner) {
            when (it.getContentIfNotHandled()?.status) {
                Status.LOADING -> {
                    binding.progressDetails.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressDetails.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.progressDetails.visibility = View.GONE

                    binding.movie = it.peekContent().data
                }
            }
        }
    }

    companion object {
        @SuppressLint("SetTextI18n")
        @JvmStatic
        @BindingAdapter("setGenres")
        fun setGenres(textView: MaterialTextView, list: List<Genre>?) {
            textView.text = "${textView.context.getString(R.string.genres)} ${
                list?.joinToString {
                    it.name
                }
            }"
        }
    }
}