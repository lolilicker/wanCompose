package com.lolilicker.wanjetpackcompose.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import com.lolilicker.wanjetpackcompose.R
import com.lolilicker.wanjetpackcompose.Screen
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.widget.listItemButton

@Composable
fun welcomePage(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        listItemButton(text = stringResource(R.string.hatching)) {
            navController.navigate(Screen.Hatching.route)
            Pref.ofUser().edit {
                putString("default_page", Screen.Hatching.route)
            }
        }
        listItemButton(text = stringResource(R.string.daughter_born), clickable = false) {
            navController.navigate(Screen.Infant.route)
            Pref.ofUser().edit {
                putString("default_page", Screen.Infant.route)
            }
        }
    }
}

