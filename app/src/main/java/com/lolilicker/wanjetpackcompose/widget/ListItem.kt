package com.lolilicker.wanjetpackcompose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme

@Composable
fun listItemButton(
    text: String,
    fontSize: TextUnit = MaterialTheme.typography.h4.fontSize,
    clickable: Boolean = true,
    onClick: () -> Unit = {}
) {
    Text(text = text,
        fontSize = fontSize,
        color = WeComposeTheme.colors.textPrimary,
        modifier = Modifier
            .background(WeComposeTheme.colors.listItem)
            .clickable(enabled = clickable) {
                onClick()
            }
            .padding(25.dp)
            .fillMaxWidth()
    )
}