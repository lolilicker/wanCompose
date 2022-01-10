package com.lolilicker.wanjetpackcompose.widget

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun PageTopBar(text: String) {
    TopAppBar(title = {
        Text(text = text)
    })
}