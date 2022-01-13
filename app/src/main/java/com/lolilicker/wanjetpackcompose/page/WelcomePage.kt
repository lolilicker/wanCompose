package com.lolilicker.wanjetpackcompose.page

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.layout.Box
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lolilicker.wanjetpackcompose.R
import com.lolilicker.wanjetpackcompose.Screen
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref.dataStore
import com.lolilicker.wanjetpackcompose.widget.listItemButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun welcomePage(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        listItemButton(text = stringResource(R.string.hatching)) {
            val page = Screen.Hatching
            navController.navigate(page.route)
            saveDefaultPage(context, scope, page)
        }
        listItemButton(text = stringResource(R.string.daughter_born), clickable = false) {
            val page = Screen.Infant
            navController.navigate(page.route)
            saveDefaultPage(context, scope, page)
        }
    }
}

private fun saveDefaultPage(context: Context, coroutineScope: CoroutineScope, page: Screen) {
    coroutineScope.launch {
        context.dataStore.edit {
            it[stringPreferencesKey(Pref.DEFAULT_PAGE)] = Screen.Hatching.route
        }
    }
}

@Preview("preview")
@Composable
fun page() {
    welcomePage(navController = rememberNavController())
}