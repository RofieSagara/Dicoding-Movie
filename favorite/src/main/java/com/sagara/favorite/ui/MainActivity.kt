package com.sagara.favorite.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sagara.favorite.R
import com.sagara.favorite.adapter.FavoriteAdapter
import com.sagara.favorite.base.BaseActivity
import com.sagara.favorite.model.Favorites
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = FavoriteAdapter(this::clickAdapter)

        main_rv.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
        main_rv.adapter = adapter

        viewModel.favorite.observe(this, Observer {
            adapter.updateList(it)
        })
    }

    private fun clickAdapter(item: Favorites){
    }
}
