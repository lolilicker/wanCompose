package com.lolilicker.wanjetpackcompose.page

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.lolilicker.wanjetpackcompose.WanViewModel
import com.lolilicker.wanjetpackcompose.extensions.activity
import com.lolilicker.wanjetpackcompose.utils.DateUtils
import com.lolilicker.wanjetpackcompose.widget.DatePicker
import com.lolilicker.wanjetpackcompose.widget.GlanceWidgetReceiver
import com.lolilicker.wanjetpackcompose.widget.listItemButton
import com.rengwuxian.wecompose.ui.theme.Shapes
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme.colors
import java.util.*

@Composable
fun hatchingPage(navController: NavController) {
    val viewModel: HatchingViewModel = viewModel()
    contentView()

    if (viewModel.showDatePicker) {
        datePickerView()
    }
}

@Composable
private fun contentView() {
    Column(
        Modifier
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        val hatchingViewModel: HatchingViewModel = viewModel()
        val wanViewModel: WanViewModel = viewModel()

        if (wanViewModel.lastPeriodDate.value == null) {
            listItemButton(
                "最后一次大姨妈哪天来哒？",
                fontSize = MaterialTheme.typography.h5.fontSize
            ) {
                hatchingViewModel.showDatePicker = true
            }
        } else {
            listItemButton(
                wanViewModel.grownTimeString,
                fontSize = MaterialTheme.typography.h4.fontSize
            )
            listItemButton(
                wanViewModel.dueTimeString,
                fontSize = MaterialTheme.typography.h4.fontSize
            )

            Spacer(Modifier.size(16.dp))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Text(
                    text = "添加到桌面 ->",
                    Modifier
                        .align(Alignment.End)
                        .background(
                            color = WeComposeTheme.colors.listItem,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            hatchingViewModel.pinToLauncher()
                        }
                        .padding(8.dp),
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    color = colors.textPrimary
                )
            }
        }

    }
}

@Composable
private fun datePickerView() {
    val hatchingViewModel: HatchingViewModel = viewModel()
    val wanViewModel: WanViewModel = viewModel()
    DatePicker(onDateSelected = {
        hatchingViewModel.showDatePicker = false
        wanViewModel.lastPeriodDate.value = it
        wanViewModel.recalculateDate()
    }) {
        hatchingViewModel.showDatePicker = false
    }
}

class HatchingViewModel : ViewModel() {
    var showDatePicker by mutableStateOf(false)

    @RequiresApi(Build.VERSION_CODES.O)
    fun pinToLauncher() {
        val appWidgetManager = AppWidgetManager.getInstance(activity)
        val myProvider = ComponentName(activity, GlanceWidgetReceiver::class.java)

        if (appWidgetManager.isRequestPinAppWidgetSupported()) {
            // Create the PendingIntent object only if your app needs to be notified
            // that the user allowed the widget to be pinned. Note that, if the pinning
            // operation fails, your app isn't notified. This callback receives the ID
            // of the newly-pinned widget (EXTRA_APPWIDGET_ID).
            val successCallback = PendingIntent.getBroadcast(
                /* context = */ activity,
                /* requestCode = */ 0,
                /* intent = */ Intent(activity, GlanceWidgetReceiver::class.java),
                /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT
            )

            appWidgetManager.requestPinAppWidget(myProvider, null, successCallback)
        }
    }
}

@Preview("preview")
@Composable
fun preview() {
    contentView()
}