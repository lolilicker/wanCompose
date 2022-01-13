package com.lolilicker.wanjetpackcompose.application

import android.app.Application

class App : Application() {
    companion object {
        var instance: Application? = null
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}