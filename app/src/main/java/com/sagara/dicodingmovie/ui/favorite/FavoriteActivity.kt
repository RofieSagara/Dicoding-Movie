package com.sagara.dicodingmovie.ui.favorite

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.adapter.FragmentAdapter
import com.sagara.dicodingmovie.ui.favorite.favoritelist.FavoriteListFragment
import com.sagara.dicodingmovie.ui.favorite.favoritetvshows.FavoriteTvShowsFragment
import com.sagara.dicodingmovie.ui.widget.FavoriteWidget
import kotlinx.android.synthetic.main.activity_favorite.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FragmentAdapter
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setSupportActionBar(favorite_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(FavoriteListFragment.newInstance(), applicationContext.getString(R.string.movies))
        adapter.addFragment(FavoriteTvShowsFragment.newInstance(), applicationContext.getString(R.string.tv_series))

        favorite_view_pager.adapter = adapter
        favorite_tab.setupWithViewPager(favorite_view_pager)

        viewModel.favorite.observe(this, Observer {
            Timber.i("Update widget")
            val widgetId = AppWidgetManager.getInstance(this)
                .getAppWidgetIds(ComponentName(baseContext, FavoriteWidget::class.java))

            val intent = Intent(baseContext, FavoriteWidget::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetId)
            sendBroadcast(intent)
        })
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
