package com.sagara.dicodingmovie.ui.list.movie

import android.content.Intent
import com.sagara.dicodingmovie.service.response.Movie
import com.sagara.dicodingmovie.ui.details.DetailsActivity
import com.sagara.dicodingmovie.ui.list.ListItemFragment
import com.sagara.dicodingmovie.ui.list.ListItemViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : ListItemFragment<MovieViewModel>(MovieViewModel::class.java) {
    private val viewModel: MovieViewModel by viewModel()

    companion object {
        fun newInstance() = MovieFragment()
    }

    override fun onClickAdapter(item: Movie) {
        Intent(context, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.ITEM, item)
            putExtra(DetailsActivity.TYPE, "movie")
        }.also {
            startActivity(it)
        }
    }

    override fun getViewModel(): ListItemViewModel = viewModel
}
