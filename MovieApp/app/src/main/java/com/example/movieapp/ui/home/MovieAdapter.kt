package com.example.movieapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ItemHomeRecyclerViewBinding
import com.example.movieapp.model.MovieItem
import com.example.movieapp.util.loadCircleImage


interface MovieClickListener {
    fun onMovieClicked(movieId : Int? )
}
class MovieAdapter(private val movieList: List<MovieItem?>, private val movieClickListener: MovieClickListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){
    class ViewHolder(val binding : ItemHomeRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHomeRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie = movieList?.get(position)

        if (movie != null) {
            holder.binding.textViewMovieTitle.text = movie.title
        }
        if (movie != null) {
            holder.binding.textViewMovieOverview.text = movie.overview
        }
        if (movie != null) {
            holder.binding.textViewMovieVote.text = movie.voteAverage.toString()
        }

        if (movie != null) {
            holder.binding.imageViewMovie.loadCircleImage(movie.posterPath)

            holder.binding.root.setOnClickListener {
                movieClickListener.onMovieClicked(movieId = movie.id)
            }
        }

    }
}