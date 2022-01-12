package com.lolilicker.wanjetpackcompose.page

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.lolilicker.wanjetpackcompose.WanViewModel
import com.lolilicker.wanjetpackcompose.receiver.GlanceWidgetReceiver
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.widget.DatePicker
import com.lolilicker.wanjetpackcompose.widget.listItemButton
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme.colors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            .fillMaxSize()
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !hatchingViewModel.appwidgetAdded
            ) {
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()
                Text(
                    text = "添加到桌面 ->",
                    Modifier
                        .align(Alignment.End)
                        .background(
                            color = WeComposeTheme.colors.listItem,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            hatchingViewModel.pinToLauncher(context)
                            hatchingViewModel.checkAppwidget(coroutineScope)
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
    var appwidgetAdded by mutableStateOf(
        Pref.ofUser()
            .getBoolean("app_widget_added", false)
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun pinToLauncher(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val myProvider = ComponentName(context, GlanceWidgetReceiver::class.java)

        if (appWidgetManager.isRequestPinAppWidgetSupported()) {
            // Create the PendingIntent object only if your app needs to be notified
            // that the user allowed the widget to be pinned. Note that, if the pinning
            // operation fails, your app isn't notified. This callback receives the ID
            // of the newly-pinned widget (EXTRA_APPWIDGET_ID).
            val successCallback = PendingIntent.getBroadcast(
                /* context = */ context,
                /* requestCode = */ 0,
                /* intent = */ Intent(context, GlanceWidgetReceiver::class.java),
                /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT
            )

            appWidgetManager.requestPinAppWidget(myProvider, null, successCallback)
        }
        Toast.makeText(context, "没有的话检查下是不是给了“创建桌面快捷方式”的权限", Toast.LENGTH_SHORT).show()
    }

    fun checkAppwidget(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            delay(500)
            appwidgetAdded = Pref.ofUser()
                .getBoolean("app_widget_added", false)
        }
    }
}

@Preview("preview")
@Composable
fun preview() {
    contentView()
}