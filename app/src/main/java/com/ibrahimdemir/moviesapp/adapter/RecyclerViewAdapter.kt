package com.ibrahimdemir.moviesapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ibrahimdemir.moviesapp.R
import com.ibrahimdemir.moviesapp.model.MovieSearchResponse
import com.ibrahimdemir.moviesapp.util.ApiConstants
import com.ibrahimdemir.moviesapp.view.MoviesDetailActivity
import kotlinx.android.synthetic.main.item_layout.view.*

class RecyclerViewAdapter(
    private val movieSearchResponse: MovieSearchResponse? = null,
    private val activity: Activity?
) : RecyclerView.Adapter<RecyclerViewAdapter.DataViewHolder>() {

    class DataViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        //no-op
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_layout, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.view.movieTitleText.text = movieSearchResponse?.results?.get(position)?.title
        holder.view.movieRatingText.text =
            movieSearchResponse?.results?.get(position)?.vote_average.toString()
        movieSearchResponse?.results?.get(position)?.poster_path?.let {
            loadMovieImage(it, holder.view, holder.view.moviePosterImageView)
        } ?: run {
            holder.view.moviePosterImageView.setImageResource(R.mipmap.icon)
        }

        holder.view.movieLayout.setOnClickListener {
            val intent = Intent(holder.itemView.context, MoviesDetailActivity::class.java)
            intent.putExtra("movieId", movieSearchResponse?.results?.get(position)?.id)
            activity?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movieSearchResponse?.results?.size ?: 0
    }

    private fun loadMovieImage(path: String?, view: View, imageView: ImageView) {
        Glide.with(view).load(ApiConstants.IMAGE_BASE_URL + path).into(imageView)
    }
}