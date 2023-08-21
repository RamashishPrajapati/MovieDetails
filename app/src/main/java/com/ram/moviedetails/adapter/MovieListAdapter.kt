package com.ram.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ram.moviedetails.R
import com.ram.moviedetails.databinding.ItemMovieListBinding
import com.ram.moviedetails.model.Search

class MovieListAdapter(onMovieClick: OnMovieClick) :
    PagingDataAdapter<Search, MovieListAdapter.ItemViewHolder>(MovieDiffCallBack()) {

    var onMovieClick: OnMovieClick? = null

    init {
        this.onMovieClick = onMovieClick
    }

    class ItemViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Search?, onMovieClick: OnMovieClick?) {
            binding.search = item

            binding.clMain.setOnClickListener {
                onMovieClick?.let {
                    it.onClick(item!!.imdbID)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movie_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), onMovieClick)

    }
}

class MovieDiffCallBack : DiffUtil.ItemCallback<Search>() {
    override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
        return oldItem.imdbID == newItem.imdbID
    }

    override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
        return oldItem.imdbID == newItem.imdbID
    }

}

interface OnMovieClick {
    fun onClick(imdId: String)

}