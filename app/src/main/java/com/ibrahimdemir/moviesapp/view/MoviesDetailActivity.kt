package com.ibrahimdemir.moviesapp.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ibrahimdemir.moviesapp.R
import com.ibrahimdemir.moviesapp.base.BaseActivity
import com.ibrahimdemir.moviesapp.extensions.hide
import com.ibrahimdemir.moviesapp.extensions.show
import com.ibrahimdemir.moviesapp.model.GenresList
import com.ibrahimdemir.moviesapp.util.ApiConstants
import com.ibrahimdemir.moviesapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_movies_detail.*

class MoviesDetailActivity : BaseActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private var movieId: Int? = null

    override fun getLayoutId(): Int = R.layout.activity_movies_detail

    override fun initView() {
        movieId = intent.getIntExtra("movieId", 0)
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        initViewModel()
        setToolbar()
        observeLiveData()
    }

    private fun initViewModel() {
        detailViewModel.fetchMovieDetail(movieId)
    }

    private fun setToolbar() {
        val mToolbar: Toolbar = findViewById<View>(R.id.customToolbar) as Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun observeLiveData() {
        detailViewModel.dataLoading.observe(this, Observer {
            it?.let {
                if (it) {
                    showProgressBar()
                }
            }
        })
        detailViewModel.dataSuccess.observe(this, Observer {
            it?.let {
                if (it) {
                    hideProgressBar()
                    detailViewModel.movieDetailResponse.observe(this, Observer { response ->
                        val moviePosterImageView =
                            movieDetailItem.findViewById<ImageView>(R.id.moviePosterImageView)
                        val movieTitleText =
                            movieDetailItem.findViewById<TextView>(R.id.movieTitleText)
                        val movieRatingText =
                            movieDetailItem.findViewById<TextView>(R.id.movieRatingText)

                        moviesDetailToolbar.findViewById<TextView>(R.id.customToolbarTitle).text =
                            response.original_title
                        loadMovieImage(response.poster_path, this, moviePosterImageView)
                        movieRatingText.text = response.vote_average.toString()
                        overviewTextView.text = response.overview
                        movieTitleText.hide()
                        movieDetailItem.show()
                        if (!response?.homepage.isNullOrBlank()) {
                            homePageLinkText.apply {
                                show()
                                setOnClickListener {
                                    val browserIntent = Intent(
                                        Intent.ACTION_VIEW, Uri.parse(response?.homepage)
                                    )
                                    startActivity(browserIntent)
                                }
                            }
                        }
                        setGenresList(response.genres)
                    })
                }
            }
        })
        detailViewModel.dataError.observe(this, Observer {
            it?.let {
                if (it) {
                    hideProgressBar()
                }
            }
        })
    }

    private fun setGenresList(genresList: List<GenresList>?) {
        genresList.takeIf { !it.isNullOrEmpty() }?.let { list ->
            for (i in list) {
                val rowView = LayoutInflater.from(this).inflate(R.layout.row_content, null)
                val genreText: TextView = rowView.findViewById(R.id.genreText)
                genreText.text = i.name
                genresLayout.apply {
                    addView(rowView)
                    show()
                }
            }
        }
    }

    private fun loadMovieImage(path: String?, context: Context, imageView: ImageView) {
        Glide.with(context).load(ApiConstants.IMAGE_BASE_URL + path).into(imageView)
    }
}