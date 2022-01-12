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
        const val GROWN_TIME_STRING = "grown_time_string"
        const val DUE_TIME_STRING = "due_time_string"
    }

    var lastPeriodDate: MutableState<Date?> =
        mutableStateOf(
            if (Pref.ofUser().getLong("last_period_date", 0) > 0) {
                Date(Pref.ofUser().getLong("last_period_date", 0))
            } else {
                null
            }
        )

    var grownTimeString by mutableStateOf(Pref.ofUser().getString(GROWN_TIME_STRING, "") ?: "")
    var dueTimeString by mutableStateOf(Pref.ofUser().getString(DUE_TIME_STRING, "") ?: "")

    init {
        recalculateDate()
    }

    fun recalculateDate() {
        val periodDate = lastPeriodDate.value ?: return
        grownTimeString = "咱孩儿已经有${DateUtils.getWeeksBetween(Date(), periodDate)}啦"
        dueTimeString =
            "离预产期还有 ${
                DateUtils.getWeeksBetween(
                    Date(periodDate.time + 40 * DateUtils.ONE_WEEK_TIME),
                    Date()
                )
            }，加油！"
        Pref.ofUser().edit {
            putLong(LATEST_PERIOD_DATE, periodDate.time)
            putString(GROWN_TIME_STRING, grownTimeString)
            putString(DUE_TIME_STRING, dueTimeString)
        }
        LocalBroadcastManager.getInstance(activity).sendBroadcast(Intent().apply {
            action = "android.appwidget.action.APPWIDGET_UPDATE"
        })
    }
}