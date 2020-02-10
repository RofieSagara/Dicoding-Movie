package com.sagara.dicodingmovie.ui.favorite.favoritetvshows

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.adapter.FavoriteAdapter
import com.sagara.dicodingmovie.adapter.FavoriteTvShowAdapter
import com.sagara.dicodingmovie.room.entitiy.FavoritesTvShow
import com.sagara.dicodingmovie.service.response.Movie
import com.sagara.dicodingmovie.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.list_item_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTvShowsFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteTvShowsFragment()
    }

    private val viewModel: FavoriteTvShowsViewModel by viewModel()
    private lateinit var adapter: FavoriteTvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteTvShowAdapter(this::onClickAdapter)

        list_rv.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
        list_rv.adapter = adapter

        viewModel.tvShows.observe(this, Observer {
            adapter.updateList(it)
        })
    }

    private fun onClickAdapter(item: FavoritesTvShow){
        Intent(context, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.ITEM, Movie.fromFavorite(item))
            putExtra(DetailsActivity.TYPE, "tv")
        }.also {
            startActivity(it)
        }
    }

}
