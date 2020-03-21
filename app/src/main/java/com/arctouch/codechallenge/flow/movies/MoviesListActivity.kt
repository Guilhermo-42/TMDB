package com.arctouch.codechallenge.flow.movies

import android.content.Context
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.extensions.goToActivityWithData
import com.arctouch.codechallenge.flow.movies.details.MovieDetailsActivity
import com.arctouch.codechallenge.flow.movies.details.MovieDetailsActivity.Companion.MOVIE_ID
import kotlinx.android.synthetic.main.activity_movies_list.*
import kotlinx.android.synthetic.main.movie_list_main_state_toolbar.*
import kotlinx.android.synthetic.main.movie_list_search_state_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListActivity : AppCompatActivity(), MoviesListAdapter.OnMovieClickListener {

    private val viewModel by viewModel<MovieListViewModel>()

    private val adapter: MoviesListAdapter by lazy {
        MoviesListAdapter(this)
    }

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)
        setupViews()
        lifecycle.addObserver(viewModel)

        viewModel.liveMoviesPresenter.observe(this, Observer {
            updateUi(it)
        })
    }

    private fun setupViews() {
        moviesList.adapter = adapter
        movieListMainStateSearchButton.setOnClickListener {
            includeMovieListSearchState.visibility = View.VISIBLE
            includeMovieListMainState.visibility = View.GONE
        }
        movieListSearchExitButton.setOnClickListener {
            includeMovieListSearchState.visibility = View.GONE
            includeMovieListMainState.visibility = View.VISIBLE
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                movieListSearchTextInputLayout.windowToken, 0
            )
            movieListSearchTextInputEditText.setText("")
        }
        movieListSearchTextInputEditText.addTextChangedListener {
            viewModel.onSearchTextChanged(it.toString())
        }
        moviesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager: LinearLayoutManager =
                    moviesList.layoutManager as LinearLayoutManager
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                        isLoading = true
                        viewModel.onListFinalReached()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }

        })
    }

    private fun updateUi(presenter: MoviesListPresenter) {
        if (isLoading) {
            isLoading = false
        }

        adapter.updateMovies(presenter.movies)

        movieListProgressBar.visibility = View.GONE
        if (presenter.movies.isEmpty()) {
            moviesListEmptyState.visibility = View.VISIBLE
            moviesList.visibility = View.GONE
        } else {
            moviesListEmptyState.visibility = View.GONE
            moviesList.visibility = View.VISIBLE
        }

    }

    override fun onItemClicked(movieId: String) {
        goToActivityWithData(MovieDetailsActivity::class.java, Bundle().apply {
            this.putString(MOVIE_ID, movieId)
        })
    }

}
