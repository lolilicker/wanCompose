package com.lolilicker.wanjetpackcompose.widget

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
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lolilicker.wanjetpackcompose.WanViewModel
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme.colors
import com.rengwuxian.wecompose.ui.theme.white1
import java.util.*

class GlanceWidget : GlanceAppWidget() {
    companion object {
    }

    @Composable
    override fun Content() {
        Column(
            GlanceModifier.background(Color.Transparent)
                .padding(10.dp).fillMaxSize()
        ) {
            Text(
                text = Pref.ofUser().getString(WanViewModel.GROWN_TIME_STRING, "") ?: "",
                style = TextStyle(fontSize = 12.sp, color = ColorProvider(colors.listItem)),
                modifier = GlanceModifier.wrapContentWidth()
            )
            Text(
                text = Pref.ofUser().getString(WanViewModel.DUE_TIME_STRING, "") ?: "",
                style = TextStyle(fontSize = 12.sp, color = ColorProvider(colors.listItem)),
                modifier = GlanceModifier.wrapContentWidth()
            )
        }

    }
}

class GlanceWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = GlanceWidget()
}