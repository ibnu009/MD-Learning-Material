package com.ibnu.distory.presentation.widget

import android.content.Intent
import android.widget.RemoteViewsService

class RecentStoriesWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        RecentStoriesWidgetViewFactory(this.applicationContext)
}