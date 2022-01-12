package com.lolilicker.wanjetpackcompose

import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.glance.action.Action
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.lolilicker.wanjetpackcompose.extensions.activity
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.utils.DateUtils
import java.util.*

class WanViewModel : ViewModel() {
    companion object {
        const val LATEST_PERIOD_DATE = "last_period_date"
    }

    var lastPeriodDate: MutableState<Date?> =
        mutableStateOf(
            if (Pref.ofUser().getLong("last_period_date", 0) > 0) {
                Date(Pref.ofUser().getLong("last_period_date", 0))
            } else {
                null
            }
        )

    var grownTimeString by mutableStateOf(
        DateUtils.formatGrownDateString(lastPeriodDate.value) ?: ""
    )
    var dueTimeString by mutableStateOf(DateUtils.formatDueDateString(lastPeriodDate.value) ?: "")

    init {
        recalculateDate()
    }

    fun recalculateDate() {
        val periodDate = lastPeriodDate.value ?: return
        grownTimeString = DateUtils.formatGrownDateString(periodDate) ?: ""
        dueTimeString = DateUtils.formatDueDateString(periodDate) ?: ""

        Pref.ofUser().edit {
            putLong(LATEST_PERIOD_DATE, periodDate.time)
        }
        LocalBroadcastManager.getInstance(activity).sendBroadcast(Intent().apply {
            action = "android.appwidget.action.APPWIDGET_UPDATE"
        })
    }
}