package com.lolilicker.wanjetpackcompose.storage.sharedpreferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

object Pref {
    const val LATEST_PERIOD_DATE = "latest_period_date"
    const val APP_WIDGET_ADDED = "app_widget_added"
    const val DEFAULT_PAGE = "default_page"

    // At the top level of your kotlin file:
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    fun readLatestPeriodData(context: Context): Flow<Long> =
        context.dataStore.data
            .map { preferences ->
                preferences[longPreferencesKey(LATEST_PERIOD_DATE)] ?: 0L
            }

    suspend fun saveLatestPeriodData(context: Context, date: Long) =
        context.dataStore.edit {
            it[longPreferencesKey(LATEST_PERIOD_DATE)] = date
        }

}