package com.sagara.dicodingmovie.ui.widget.remoteview

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViewsService

class StackWidgetService: RemoteViewsService() {
    private var appWidgetId = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        appWidgetId = intent?.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0) ?: 0
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory =
        StackRemoteViewFactory(this.applicationContext, appWidgetId)
}