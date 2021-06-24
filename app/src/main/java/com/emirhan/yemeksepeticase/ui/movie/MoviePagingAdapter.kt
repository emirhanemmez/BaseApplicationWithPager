package com.emirhan.yemeksepeticase.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emirhan.yemeksepeticase.BR
import com.emirhan.yemeksepeticase.data.Movie
import com.emirhan.yemeksepeticase.databinding.ListItemMovieBinding

class MoviePagingAdapter :
    PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(DIFF_UTIL) {

    private lateinit var onClick: (Int) -> Unit

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)

        holder.viewDataBinding.setVariable(BR.movie, data)

        holder.viewDataBinding.root.setOnClickListener {
            onClick(data?.id!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    inner class MovieViewHolder(val viewDataBinding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    fun onMovieClick(listener: (Int) -> Unit) {
        onClick = listener
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, item: Movie): Boolean {
                return oldItem.id == item.id
            }

            override fun areContentsTheSame(oldItem: Movie, item: Movie): Boolean {
                return oldItem == item
            }
        }
    }
}