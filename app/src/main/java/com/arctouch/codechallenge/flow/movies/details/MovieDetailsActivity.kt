package com.arctouch.codechallenge.flow.movies.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "movie_id"
    }

    private val viewModel by viewModel<MovieDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setupViews()

        viewModel.livePresenter.observe(this, Observer {
            updateUi(it)
        })

        retrieveData()
    }

    private fun setupViews() {
        setSupportActionBar(movieDetailsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun retrieveData() {
        val movieId = intent.getStringExtra(MOVIE_ID) ?: return

        viewModel.retrieveMovieDetails(movieId)
    }

    private fun updateUi(presenter: MovieDetailsPresenter) {
        Glide.with(this)
            .load(presenter.moviePosterPath)
            .into(movieDetailsPosterImage)

        Glide.with(this)
            .load(presenter.movieCompanyImage)
            .into(movieDetailsProductionCompanyLogo)

        movieDetailsTitle.text = presenter.movieName
        movieDetailsTagline.text = presenter.movieTagline
        movieDetailsPopularity.text =
            getString(R.string.movie_details_popularity, presenter.moviePopularity)
        movieDetailsReleaseDate.text =
            getString(R.string.movie_details_release_date, presenter.movieReleaseDate)
        movieDetailsVoteAverage.text =
            getString(R.string.movie_details_vote_average, presenter.movieVoteAverage)
        movieDetailsRuntime.text = getString(R.string.movie_details_runtime, presenter.movieRuntime)
        movieDetailsDescription.text = presenter.movieDescription
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

}
