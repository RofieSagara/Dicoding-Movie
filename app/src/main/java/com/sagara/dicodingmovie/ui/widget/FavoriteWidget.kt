package com.sagara.dicodingmovie.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.ui.widget.remoteview.StackWidgetService
import timber.log.Timber

/**
 * Implementation of App Widget functionality.
 */
class FavoriteWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        Timber.i("onUpdate FavoriteWidget")
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            Timber.i("Update widget id: $appWidgetId")
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.favorite_widget)
            views.setRemoteAdapter(R.id.favorite_widget_stack, intent)
            // Instruct the widget manager to update the widget
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.favorite_widget_stack)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

