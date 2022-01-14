package com.lolilicker.wanjetpackcompose

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref.dataStore
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref.readLatestPeriodData
import com.lolilicker.wanjetpackcompose.utils.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class WanViewModel : ViewModel() {
    var defaultTab: MutableState<String?> = mutableStateOf(null)

    var lastPeriodDate: Date? by mutableStateOf(null)
    var grownTimeString by mutableStateOf("")
    var dueTimeString by mutableStateOf("")

    fun recalculateDate(context: Context) {
        viewModelScope.launch {
            readLatestPeriodData(context).collect {
                val periodDate = if (it == 0L) {
                    null
                } else {
                    Date(it)
                }

                lastPeriodDate = periodDate
                grownTimeString = DateUtils.formatGrownDateString(periodDate) ?: ""
                dueTimeString = DateUtils.formatDueDateString(periodDate) ?: ""

                context.sendBroadcast(Intent().apply {
                    action = "android.appwidget.action.APPWIDGET_UPDATE"
                })
            }
        }
    }
}