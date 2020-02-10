package com.sagara.dicodingmovie.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.adapter.MovieRecyclerViewAdapter
import com.sagara.dicodingmovie.base.BaseActivity
import com.sagara.dicodingmovie.service.response.Movie
import com.sagara.dicodingmovie.ui.details.DetailsActivity
import com.sagara.dicodingmovie.utils.livedata.EventObserver
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchActivity : BaseActivity() {

    private val viewModel: SearchViewModel by viewModel()
    private lateinit var adapter: MovieRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(search_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        adapter = MovieRecyclerViewAdapter(this::onClickAdapter)
        search_rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        search_rv.adapter = adapter

        viewModel.state.observe(this, EventObserver {
            when(it){
                is SearchState.Error -> {
                    Snackbar.make(search_root, it.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        })

        viewModel.search.observe(this, Observer {
            adapter.updateList(it)
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Timber.i("Create search menu")

        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView = searchItem?.actionView as SearchView

        //Implement style color in search view
        val searchAutoComplete = searchView
            .findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)
        searchAutoComplete.setTextColor(Color.WHITE)
        searchAutoComplete.setHintTextColor(Color.GRAY)

        val closeImageView = searchView
            .findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        closeImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_close))

        val collapseIcon = searchView
            .findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        collapseIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search))
        //Implement style color in search view

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        if (viewModel.searchQuery != "") {
            searchItem.expandActionView()
            searchView.setQuery(viewModel.searchQuery, false)
            searchView.clearFocus()
        }

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            private var searchFor = ""

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText?.trim() ?: ""
                viewModel.searchQuery = searchText
                if(searchText == searchFor) return false

                searchFor = searchText
                launch(Dispatchers.IO) {
                    delay(500)
                    if(searchText != searchFor) return@launch

                    if (searchFor == "") return@launch

                    viewModel.search(searchFor)
                    Timber.i("Search movie and tv $searchFor")
                }
                return false

            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
        return super.onPrepareOptionsMenu(menu)
    }

    private fun onClickAdapter(item: Movie) {
        Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.ITEM, item)
            putExtra(DetailsActivity.TYPE, "movie")
        }.also {
            startActivity(it)
        }
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }
}
