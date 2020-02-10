package com.sagara.dicodingmovie.ui.widget.remoteview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.room.AppDatabase
import com.sagara.dicodingmovie.room.IAppDatabase
import com.sagara.dicodingmovie.service.response.Movie
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class StackRemoteViewFactory(
    private val context: Context,
    private val appWidgetId: Int
): RemoteViewsService.RemoteViewsFactory {

    private val data = mutableListOf<Bitmap?>()
    private val localDatabase: IAppDatabase = AppDatabase.getInstance(context)

    private fun getBitmapFromURL(src: String): Bitmap? {
        return try {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            Timber.e(e)
            null
        }
    }

    override fun getCount(): Int = data.size

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewAt(p0: Int): RemoteViews {
        Timber.i("Update view stack view")
        val bitmap = data[p0]
        val remote = RemoteViews(context.packageName, R.layout.item_favorite_widget)
        remote.setImageViewBitmap(R.id.item_favorite_widget_image, bitmap)

        val extras = Bundle()
        extras.putInt(context.packageName, p0)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        remote.setOnClickFillInIntent(R.id.item_favorite_widget_image, fillInIntent)
        return remote
    }

    override fun getViewTypeCount(): Int = 1

    override fun hasStableIds(): Boolean = false

    override fun onCreate() {
        Timber.i("Favorite widget created")
    }

    override fun onDataSetChanged() {
        Timber.i("onDataSetChanged called do fetch new data in database!")
        val indent = Binder.clearCallingIdentity()

        val tv = localDatabase.favoriteTvShowDao()
            .loadStatic().map { getBitmapFromURL("https://image.tmdb.org/t/p/w92${it.poster}") }
        val movie = localDatabase.favoriteDao()
            .loadStatic().map { getBitmapFromURL("https://image.tmdb.org/t/p/w92${it.poster}") }
        data.clear()
        data.addAll(tv)
        data.addAll(movie)

        Binder.restoreCallingIdentity(indent)
    }

    override fun onDestroy() {
        Timber.i("Favorite widget destroyed")
    }
}