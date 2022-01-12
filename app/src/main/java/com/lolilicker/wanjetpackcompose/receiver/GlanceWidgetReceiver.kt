package com.lolilicker.wanjetpackcompose.receiver

import android.content.Context
import android.content.Intent
import androidx.core.content.edit
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.utils.DateUtils
import com.lolilicker.wanjetpackcompose.widget.GlanceWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GlanceWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = GlanceWidget()
    private var ticking = false

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Pref.ofUser().edit {
            putBoolean("app_widget_added", true)
        }
        ticking = true
        tick(context)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        ticking = false
        Pref.ofUser().edit {
            putBoolean("app_widget_added", false)
        }
    }

    private fun tick(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(DateUtils.ONE_HOUR_TIME)
            glanceAppWidget.updateAll(context = context)
            if (ticking) {
                tick(context)
            }
        }
    }

}