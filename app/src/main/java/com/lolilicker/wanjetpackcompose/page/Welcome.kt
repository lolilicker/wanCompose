package com.lolilicker.wanjetpackcompose.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lolilicker.wanjetpackcompose.R
import com.lolilicker.wanjetpackcompose.Screen
import com.lolilicker.wanjetpackcompose.widget.listItemButton

@Composable
fun welcomePage(navController: NavController) {
    Surface() {
        Column(
            modifier = Modifier.padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            listItemButton(text = stringResource(R.string.hatching)) {
                navController.navigate(Screen.Hatching.route)
            }
            listItemButton(text = stringResource(R.string.daughter_born)) {
                navController.navigate(Screen.Infant.route)
            }
        }
    }
}

