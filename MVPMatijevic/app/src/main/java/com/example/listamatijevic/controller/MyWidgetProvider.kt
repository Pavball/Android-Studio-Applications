package com.example.listamatijevic.controller

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.listamatijevic.R

class MyWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // This will be called periodically based on the updatePeriodMillis defined
    }

    companion object {
        const val UPDATE_WIDGET_ACTION = "UPDATE_WIDGET"
        const val EXTRA_WIDGET_TEXT = "EXTRA_WIDGET_TEXT"
        const val EXTRA_WIDGET_IMAGE_URL = "EXTRA_WIDGET_IMAGE_URL"

        fun updateWidgetText(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray, text: String, imageUrl: String) {
            for (appWidgetId in appWidgetIds) {
                val views = RemoteViews(context.packageName, R.layout.widget_layout).apply {
                    setTextViewText(R.id.widget_text, text)
                }

                // Load the image asynchronously with Glide
                Glide.with(context.applicationContext)
                    .asBitmap()
                    .load(imageUrl)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            views.setImageViewBitmap(R.id.widget_image, resource)
                            appWidgetManager.updateAppWidget(appWidgetId, views)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // Handle cleanup if needed
                        }
                    })
            }
        }

        fun handleIntent(context: Context, intent: Intent) {
            if (intent.action == UPDATE_WIDGET_ACTION) {
                val imageUrl = intent.getStringExtra(EXTRA_WIDGET_IMAGE_URL) ?: return
                val text = intent.getStringExtra(EXTRA_WIDGET_TEXT) ?: return
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, MyWidgetProvider::class.java))
                updateWidgetText(context, appWidgetManager, appWidgetIds, text, imageUrl)
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        handleIntent(context, intent)
    }
}
