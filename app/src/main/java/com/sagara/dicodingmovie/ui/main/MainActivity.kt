package com.sagara.dicodingmovie.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.adapter.FragmentAdapter
import com.sagara.dicodingmovie.base.BaseActivity
import com.sagara.dicodingmovie.ui.favorite.FavoriteActivity
import com.sagara.dicodingmovie.ui.list.movie.MovieFragment
import com.sagara.dicodingmovie.ui.list.tvseries.TvSeriesFragment
import com.sagara.dicodingmovie.ui.search.SearchActivity
import com.sagara.dicodingmovie.ui.settings.SettingsActivity
import com.sagara.dicodingmovie.ui.settings.SettingsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)

        viewModel = ViewModelProviders
            .of(this)[MainViewModel::class.java]

        adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(MovieFragment.newInstance(), applicationContext.getString(R.string.movies))
        adapter.addFragment(TvSeriesFragment.newInstance(), applicationContext.getString(R.string.tv_series))

        main_view_pager.adapter = adapter
        main_tab.setupWithViewPager(main_view_pager)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
/*                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)*/
                Intent(this, SettingsActivity::class.java).also {
                    startActivity(it)
                }
                true
            }
            R.id.menu_favorite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
                true
            }
            R.id.menu_search -> {
                Intent(this, SearchActivity::class.java).also {
                    startActivity(it)
                }
                true
            }
            else -> true
        }
    }
}
