package com.sagara.dicodingmovie.ui.list.tvseries

import android.content.Intent
import com.sagara.dicodingmovie.service.response.Movie
import com.sagara.dicodingmovie.ui.details.DetailsActivity
import com.sagara.dicodingmovie.ui.list.ListItemFragment
import com.sagara.dicodingmovie.ui.list.ListItemViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvSeriesFragment : ListItemFragment<TvSeriesViewModel>(TvSeriesViewModel::class.java) {
    private val viewModel: TvSeriesViewModel by viewModel()

    companion object {
        fun newInstance() = TvSeriesFragment()
    }

    override fun onClickAdapter(item: Movie) {
        Intent(context, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.ITEM, item)
            putExtra(DetailsActivity.TYPE, "tv")
        }.also {
            startActivity(it)
        }
    }

    override fun getViewModel(): ListItemViewModel = viewModel
}
