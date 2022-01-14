package com.lolilicker.wanjetpackcompose

import java.util.*

object Constants {
    val DEFAULT_LAST_PERIOD_DATE = Calendar.getInstance().apply {
        set(2021, 4, 30, 0, 0, 0)
    }.timeInMillis

    val TOTAL_WEEKS = 40
}