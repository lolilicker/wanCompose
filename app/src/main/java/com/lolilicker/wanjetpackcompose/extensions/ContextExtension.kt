package com.lolilicker.wanjetpackcompose.extensions

import android.content.Context
import androidx.compose.runtime.Composable
import com.rengwuxian.wecompose.ui.theme.WeComposeColors
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme

val Context.colors: WeComposeColors
    @Composable
    get() = WeComposeTheme.colors