package com.lolilicker.wanjetpackcompose.storage.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.lolilicker.wanjetpackcompose.MainActivity
import com.lolilicker.wanjetpackcompose.extensions.activity

object Pref {
    fun ofUser(): SharedPreferences {
        return activity!!.getSharedPreferences("user", Context.MODE_PRIVATE)
    }
}