package com.lolilicker.wanjetpackcompose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lolilicker.wanjetpackcompose.page.hatchingPage
import com.lolilicker.wanjetpackcompose.page.infantPage
import com.lolilicker.wanjetpackcompose.page.welcomePage
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme

class MainActivity : ComponentActivity() {
    companion object {
        var instance: Activity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        val defaultPage =
            Pref.ofUser().getString("default_page", Screen.Welcome.route) ?: Screen.Welcome.route
        setContent {
            WeComposeTheme() {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = defaultPage) {
                    composable(Screen.Welcome.route) {
                        welcomePage(navController = navController)
                    }
                    composable(Screen.Hatching.route) {
                        hatchingPage(navController = navController)
                    }
                    composable(Screen.Infant.route) {
                        infantPage(navController = navController)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}