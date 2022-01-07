package com.lolilicker.wanjetpackcompose.utils

import java.lang.StringBuilder
import java.util.*

fun String?.format( vararg args: Any?): String {
    val reusableFormatter: ReusableFormatter =
      formatterThreadLocal.get()!!
    val string = reusableFormatter.formatter.format(this, *args).toString()
    reusableFormatter.reset()
    return string
}

var formatterThreadLocal: ThreadLocal<ReusableFormatter?> =
    object : ThreadLocal<ReusableFormatter?>() {
        override fun initialValue(): ReusableFormatter {
            return ReusableFormatter()
        }
    }

class ReusableFormatter {
    val formatter: Formatter
    private val stringBuilder: StringBuilder
    fun reset() {
        stringBuilder.setLength(0)
    }

    init {
        stringBuilder = StringBuilder()
        formatter = Formatter(stringBuilder)
    }
}