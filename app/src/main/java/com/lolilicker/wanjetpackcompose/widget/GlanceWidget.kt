package com.lolilicker.wanjetpackcompose.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.updateAll
import androidx.glance.background
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
import java.util.*

class GlanceWidget : GlanceAppWidget() {
    companion object {
    }

    @Composable
    override fun Content() {
        val periodDate = Date(Pref.ofUser().getLong(WanViewModel.LATEST_PERIOD_DATE, 0L))
        val grownTimeString = DateUtils.formatGrownDateString(periodDate) ?: ""
        val dueTimeString = DateUtils.formatDueDateString(periodDate) ?: ""
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
            Spacer(GlanceModifier.size(16.dp))
            Text(
                text = dueTimeString,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    color = ColorProvider(colors.listItem)
                ),
                modifier = GlanceModifier.wrapContentWidth()
            )
        }
    }

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition
}
