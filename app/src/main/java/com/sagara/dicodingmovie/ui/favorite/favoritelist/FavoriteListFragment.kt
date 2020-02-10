package com.sagara.dicodingmovie.ui.favorite.favoritelist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.adapter.FavoriteAdapter
import com.sagara.dicodingmovie.room.entitiy.Favorites
import com.sagara.dicodingmovie.service.response.Movie
import com.sagara.dicodingmovie.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.list_item_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteListFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteListFragment()
    }

    private val viewModel: FavoriteListViewModel by viewModel()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteAdapter(this::onClickAdapter)

        list_rv.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
        list_rv.adapter = adapter

        viewModel.movies.observe(this, Observer {
            adapter.updateList(it)
        })
    }

    private fun onClickAdapter(item: Favorites){
        Intent(context, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.ITEM, Movie.fromFavorite(item))
            putExtra(DetailsActivity.TYPE, "movie")
        }.also {
            startActivity(it)
        }
    }

}
