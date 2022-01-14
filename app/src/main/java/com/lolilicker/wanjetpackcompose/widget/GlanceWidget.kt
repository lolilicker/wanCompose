package com.lolilicker.wanjetpackcompose.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IntDef
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lolilicker.wanjetpackcompose.WanViewModel
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.utils.DateUtils
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme.colors
import com.rengwuxian.wecompose.ui.theme.white1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.StringBuilder
import java.util.*

class GlanceWidget : GlanceAppWidget() {
    @Composable
    override fun Content() {
        val prefs = currentState<Preferences>()
        val periodDateTimeMillis = prefs[longPreferencesKey(Pref.LATEST_PERIOD_DATE)]
        val periodDate = remember(periodDateTimeMillis) {
            periodDateTimeMillis?.let {
                Date(it)
            }
        }
        val grownTimeString = remember(periodDateTimeMillis) {
            DateUtils.formatGrownDateString(periodDate) ?: "最后一次大姨妈哪天来的啊"
        }
        val dueTimeString = remember(periodDateTimeMillis) {
            DateUtils.formatDueDateString(periodDate) ?: "先去配置下"
        }
        val totalDays = 40 * 7
        val daysLeft = remember {
            totalDays - DateUtils.getDaysBetween(periodDate, Date())
        }
        Column(
            GlanceModifier.background(Color.Transparent)
                .padding(10.dp).fillMaxSize()
        ) {
            Text(
                text = grownTimeString,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    color = ColorProvider(colors.listItem)
                ),
                modifier = GlanceModifier.wrapContentWidth()
            )
            Spacer(GlanceModifier.size(6.dp))
            Text(
                text = dueTimeString,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    color = ColorProvider(colors.listItem)
                ),
                modifier = GlanceModifier.wrapContentWidth()
            )
            Spacer(GlanceModifier.size(6.dp))

            progress(daysLeft, totalDays)
        }
    }

    @Composable
    private fun progress(daysLeft: Int = 0, totalDays: Int = 7 * 40) {
        val text by remember(daysLeft, totalDays) {
            val stringBuilder = StringBuilder()
            for (i in 0 until totalDays) {
                stringBuilder.append(
                    when {
                        i < (totalDays - daysLeft) -> "▨"
                        i == (totalDays - daysLeft) -> "▣"
                        else -> "□"
                    }
                )
            }
            mutableStateOf(stringBuilder.toString())
        }

        if (totalDays != 0) {
            Text(
                text = text,
                modifier = GlanceModifier,
                style = TextStyle(
                    fontSize = 8.sp,
                    color = ColorProvider(colors.listItem)
                )
            )
        }
    }

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition
}


