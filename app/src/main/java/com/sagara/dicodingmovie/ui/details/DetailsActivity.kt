package com.sagara.dicodingmovie.ui.details

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.base.BaseActivity
import com.sagara.dicodingmovie.service.response.Movie
import com.sagara.dicodingmovie.ui.widget.FavoriteWidget
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailsActivity : BaseActivity() {

    companion object {
        const val ITEM = "item"
        const val TYPE = "type"
    }

    private val viewModel: DetailsViewModel by viewModel()
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(details_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.item.observe(this, Observer {
            bind(it)
        })

        viewModel.isFavorite.observe(this, Observer {
            isFavorite = it
            invalidateOptionsMenu()
            Timber.i("Update widget")
            val widgetId = AppWidgetManager.getInstance(this)
                .getAppWidgetIds(ComponentName(baseContext, FavoriteWidget::class.java))

            val intent = Intent(baseContext, FavoriteWidget::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetId)
            sendBroadcast(intent)
        })

        val selectedMovie = intent.getParcelableExtra<Movie>(ITEM) ?: return
        val type = intent.getStringExtra(TYPE) ?: return

        launch(Dispatchers.IO) {
            viewModel.bind(selectedMovie, type)
        }
    }

    private fun bind(item: Movie){
        //! Set poster
        val uri = Uri.parse("https://image.tmdb.org/t/p/w92/${item.poster}")
        Glide.with(this)
            .load(uri)
            .into(details_poster)

        //! Set other Stuff
        details_overview.text = item.overview
        details_rating.text = StringBuilder("Rating ${item.voteAverage}% of ${item.voteCount}")
        details_year.text = item.releaseDate
        details_title.text = item.title
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (isFavorite) {
            menu?.findItem(R.id.menu_favorite)?.isVisible = true
            menu?.findItem(R.id.menu_not_favorite)?.isVisible = false
        }else{
            menu?.findItem(R.id.menu_favorite)?.isVisible = false
            menu?.findItem(R.id.menu_not_favorite)?.isVisible = true
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.favorite_menu, menu)
        return true
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
            R.id.menu_favorite -> {
                launch(Dispatchers.IO) { viewModel.unLike() }
                true
            }
            R.id.menu_not_favorite -> {
                launch(Dispatchers.IO) { viewModel.like() }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}
