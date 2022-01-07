package com.lolilicker.wanjetpackcompose.widget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import com.rengwuxian.wecompose.ui.theme.white1
import java.util.*

class GlanceWidget :GlanceAppWidget(){
    companion object{
    }

    @Composable
    override fun Content() {
        Box(GlanceModifier.background(WeComposeTheme.colors.background).padding(10.dp).fillMaxSize()) {
            Text(text = "test",modifier = GlanceModifier.fillMaxSize())
        }
    }

//    @Composable
//    override fun Content() {
//        Column(
//            modifier = GlanceModifier
//                .fillMaxSize()
//                .appWidgetBackground()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "First Glance widget",
//                modifier = GlanceModifier
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp),
//                style = TextStyle(fontWeight = FontWeight.Bold),
//            )
//        }
//    }
}

class GlanceWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget = GlanceWidget()
}