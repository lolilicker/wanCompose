package com.lolilicker.wanjetpackcompose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lolilicker.wanjetpackcompose.page.hatchingPage
import com.lolilicker.wanjetpackcompose.page.infantPage
import com.lolilicker.wanjetpackcompose.page.welcomePage
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref.dataStore
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val wanViewModel: WanViewModel = viewModel()
            wanViewModel.recalculateDate(LocalContext.current)
            LaunchedEffect(Unit) {
                dataStore.data.map {
                    it[stringPreferencesKey(Pref.DEFAULT_PAGE)]
                }.collect {
                    wanViewModel.defaultTab.value = it ?: Screen.Welcome.route
                }
            }
            WeComposeTheme() {
                val defaultPage = wanViewModel.defaultTab.value

                if (defaultPage != null) {
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
    }
}