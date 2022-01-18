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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lolilicker.wanjetpackcompose.R
import com.lolilicker.wanjetpackcompose.Screen
import com.lolilicker.wanjetpackcompose.WanViewModel
import com.lolilicker.wanjetpackcompose.receiver.GlanceWidgetReceiver
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref.dataStore
import com.lolilicker.wanjetpackcompose.widget.DatePicker
import com.lolilicker.wanjetpackcompose.widget.listItemButton
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme.colors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun hatchingPage(navController: NavController) {
    WeComposeTheme() {
        val viewModel: HatchingViewModel = viewModel()

        contentView(navController)

        if (viewModel.showDatePicker) {
            datePickerView()
        }
    }
}

@Composable
private fun contentView(
    navController: NavController,
    wanViewModel: WanViewModel = viewModel(),
    hatchingViewModel: HatchingViewModel = viewModel()
) {
    Column(
        Modifier
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        wanViewModel.recalculateDate(context = context)
        hatchingViewModel.checkAppwidget(context)

        if (wanViewModel.lastPeriodDate == null) {
            listItemButton(
                "最后一次大姨妈哪天来哒？ ->",
                fontSize = MaterialTheme.typography.h5.fontSize
            ) {
                hatchingViewModel.showDatePicker = true
            }
        } else {
            Text(
                text = "${wanViewModel.grownTimeString}\n${wanViewModel.dueTimeString}",
                modifier = Modifier
                    .background(colors.listItem)
                    .padding(25.dp)
                    .fillMaxWidth(),
                fontSize = MaterialTheme.typography.h4.fontSize,
                color = colors.textPrimary,
                lineHeight = 50.sp
            )

            Spacer(Modifier.size(16.dp))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                !hatchingViewModel.appwidgetAdded
            ) {
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
                            hatchingViewModel.checkAppwidget(context, 1000)
                        }
                        .padding(8.dp),
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    color = colors.textPrimary
                )
            }
        }

        listItemButton(text = "凯格尔回旋加速提肛运动 ->") {
            val page = Screen.Player
            navController.navigate(page.route)
        }
    }
}

@Composable
private fun datePickerView() {
    val hatchingViewModel: HatchingViewModel = viewModel()
    val wanViewModel: WanViewModel = viewModel()
    val context = LocalContext.current

    DatePicker(onDateSelected = { date ->
        hatchingViewModel.showDatePicker = false
        wanViewModel.lastPeriodDate = date
        CoroutineScope(Dispatchers.Default).launch {
            Pref.saveLatestPeriodData(context = context, date.time)
            wanViewModel.recalculateDate(context)
        }
    }) {
        hatchingViewModel.showDatePicker = false
    }
}

class HatchingViewModel : ViewModel() {
    var showDatePicker by mutableStateOf(false)
    var appwidgetAdded by mutableStateOf(false)

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

    fun checkAppwidget(context: Context, delayTime: Long = 0) {
        viewModelScope.launch {
            delay(delayTime)
            context.dataStore.data.map {
                it[booleanPreferencesKey(Pref.APP_WIDGET_ADDED)]
            }.collect {
                appwidgetAdded = it == true
            }
        }
    }
}

@Preview("preview")
@Composable
fun preview() {
    contentView(
        rememberNavController(),
        WanViewModel().apply {
            lastPeriodDate = Date()
            grownTimeString = "grownTimeString"
            dueTimeString = "dueTimeString"
        }, HatchingViewModel()
    )
}