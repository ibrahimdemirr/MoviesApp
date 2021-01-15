package com.ibrahimdemir.moviesapp.view

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ibrahimdemir.moviesapp.R
import com.ibrahimdemir.moviesapp.adapter.RecyclerViewAdapter
import com.ibrahimdemir.moviesapp.base.BaseActivity
import com.ibrahimdemir.moviesapp.extensions.*
import com.ibrahimdemir.moviesapp.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.custom_toolbar.view.*

class SearchActivity : BaseActivity() {

    private lateinit var searchViewModel: SearchViewModel

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun initView() {
        initViewModel()
        setToolbar()
        manageSearchView()
        observeLiveData()
    }

    private fun initViewModel() {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    private fun setToolbar() {
        val mToolbar: Toolbar = findViewById<View>(R.id.customToolbar) as Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToolbar.navigationIcon = null
    }

    private fun manageSearchView() {
        val editTextSearch = moviesSearchView.findViewById<EditText>(R.id.editTextSearchBox)
        val cancelImageView = moviesSearchView.findViewById<ImageView>(R.id.imageViewCancel)
        editTextSearch.listenChanges(afterTextChangedListener = { s: Editable? ->
            if (!TextUtils.isEmpty(s)) {
                cancelImageView.show()
                if (s != null && s.length > 2) {
                    searchViewModel.fetchMoviesList(s.toString())
                }
            } else {
                cancelImageView.hide()
                moviesRecyclerView.hide()
            }
        })
        cancelImageView.setOnClickListener {
            editTextSearch.clear()
            moviesRecyclerView.hide()
            emptyStateLayout.hide()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeLiveData() {
        searchViewModel.dataLoading.observe(this, Observer {
            it?.let {
                if (it) {
                    //showProgressBar()
                }
            }
        })
        searchViewModel.dataSuccess.observe(this, Observer {
            it?.let {
                if (it) {
                    hideProgressBar()
                    searchViewModel.movieSearchResponse.observe(this, Observer { apiResponse ->
                        apiResponse?.let {
                            emptyStateLayout.hide()
                            moviesRecyclerView.apply {
                                show()
                                layoutManager = GridLayoutManager(this@SearchActivity, 3)
                                adapter = RecyclerViewAdapter(apiResponse, this@SearchActivity)
                            }
                        }
                        if (apiResponse.total_pages == 0 || apiResponse.total_results == 0) {
                            emptyStateLayout.show()
                            emptyStateLayout.findViewById<TextView>(R.id.emptyTextView).text =
                                "${moviesSearchView.findViewById<EditText>(R.id.editTextSearchBox).text} could not be found"
                            val v = currentFocus
                            v?.hideKeyboard()
                        }
                    })
                }
            }
        })
        searchViewModel.dataError.observe(this, Observer {
            it?.let {
                if (it) {
                    hideProgressBar()
                    Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}