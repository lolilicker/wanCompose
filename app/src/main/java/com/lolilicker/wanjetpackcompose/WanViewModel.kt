package com.lolilicker.wanjetpackcompose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.utils.DateUtils
import java.util.*

class WanViewModel : ViewModel() {
    var lastPeriodDate: MutableState<Date?> =
        mutableStateOf(
            if (Pref.ofUser().getLong("last_period_date", 0) > 0) {
                Date(Pref.ofUser().getLong("last_period_date", 0))
            } else {
                null
            }
        )

    var grownTimeString by mutableStateOf("")
    var dueTimeString by mutableStateOf("")

    init {
        recalculateDate()
    }

    fun recalculateDate() {
        Pref.ofUser().edit {
            putLong("last_period_date", lastPeriodDate.value?.time ?: 0L)
        }
        val periodDate = lastPeriodDate.value ?: return
        grownTimeString = "咱孩儿已经有${DateUtils.getWeeksBetween(Date(), periodDate)}啦"
        dueTimeString =
            "离预产期还有 ${
                DateUtils.getWeeksBetween(
                    Date(periodDate.time + 40 * DateUtils.ONE_WEEK_TIME),
                    Date()
                )
            }，加油！"
    }
}