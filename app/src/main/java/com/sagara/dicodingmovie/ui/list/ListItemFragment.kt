package com.sagara.dicodingmovie.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.adapter.MovieRecyclerViewAdapter
import com.sagara.dicodingmovie.service.response.Movie
import com.sagara.dicodingmovie.utils.livedata.EventObserver
import kotlinx.android.synthetic.main.list_item_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class ListItemFragment<T: ListItemViewModel>(private val clazz: Class<T>) : Fragment() {
    private lateinit var adapter: MovieRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = MovieRecyclerViewAdapter(this::onClickAdapter)

        list_rv.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
        list_rv.adapter = adapter

        getViewModel().state.observe(this, EventObserver {
            when (it){
                is ListItemState.ShowLoading -> {
                    list_swipe.isRefreshing = true
                }
                is ListItemState.HideLoading -> {
                    list_swipe.isRefreshing = false
                }
                is ListItemState.Error -> {
                    Snackbar.make(list_root, it.message, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        })

        getViewModel().item.observe(this, Observer {
            adapter.updateList(it.results.map { dt -> dt.copy() })
        })

        list_swipe.setOnRefreshListener {
            GlobalScope.launch { getViewModel().refresh() }
        }
    }

    abstract fun getViewModel(): ListItemViewModel

    abstract fun onClickAdapter(item: Movie)
}
