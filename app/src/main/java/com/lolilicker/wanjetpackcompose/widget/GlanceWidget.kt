package com.lolilicker.wanjetpackcompose.widget

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.lolilicker.wanjetpackcompose.Constants
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.utils.DateUtils
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme.colors
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
        val weeksPassed = remember {
            DateUtils.getWeeksBetween(periodDate, Date())
        }
        Column(
            GlanceModifier.background(Color.Transparent)
                .padding(horizontal = 10.dp, vertical = 5.dp).fillMaxSize()
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

            progress(weeksPassed)
        }
    }

    @Composable
    private fun progress(weeksPassed: Int = 0, totalWeeks: Int = Constants.TOTAL_WEEKS) {
        val text by remember(weeksPassed, totalWeeks) {
            val stringBuilder = StringBuilder()
            for (i in 0 until totalWeeks) {
                stringBuilder.append(
                    when {
                        i < weeksPassed -> "▨"
                        i == weeksPassed -> "▣"
                        else -> "□"
                    }
                )
            }
            mutableStateOf(stringBuilder.toString())
        }

        if (totalWeeks != 0) {
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


