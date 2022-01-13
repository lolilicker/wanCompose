package com.lolilicker.wanjetpackcompose.widget

import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lolilicker.wanjetpackcompose.Constants
import com.lolilicker.wanjetpackcompose.R
import com.lolilicker.wanjetpackcompose.utils.DateUtils
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme.colors
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun DatePicker(onDateSelected: (Date) -> Unit, onDismissRequest: () -> Unit) {

    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
        dialogContent(onDateSelected = onDateSelected) {
            onDismissRequest()
        }
    }
}

@Composable
private fun dialogContent(onDateSelected: (Date) -> Unit, onDismissRequest: () -> Unit) {
    val selDate = remember { mutableStateOf(Date(Constants.DEFAULT_LAST_PERIOD_DATE)) }
    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = colors.background,
                shape = RoundedCornerShape(size = 16.dp)
            )
    ) {
        Column(
            Modifier
                .defaultMinSize(minHeight = 72.dp)
                .fillMaxWidth()
                .background(
                    color = colors.background,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = "上次大姨妈啥时候来的".toUpperCase(Locale.ENGLISH),
                style = MaterialTheme.typography.caption,
                color = colors.textPrimary
            )

            Spacer(modifier = Modifier.size(24.dp))

            Text(
                text = DateUtils.formatDate(selDate.value),
                style = MaterialTheme.typography.h4,
                color = colors.textPrimary
            )

            Spacer(modifier = Modifier.size(16.dp))
        }

        CustomCalendarView(onDateSelected = {
            selDate.value = it
        })

        Spacer(modifier = Modifier.size(8.dp))

        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 16.dp, end = 16.dp)
        ) {
            TextButton(
                onClick = onDismissRequest
            ) {
                //TODO - hardcode string
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.button,
                    color = colors.textPrimary
                )
            }

            TextButton(
                onClick = {
                    onDateSelected(selDate.value)
                    onDismissRequest()
                }
            ) {
                //TODO - hardcode string
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.button,
                    color = colors.textPrimary
                )
            }

        }
    }
}

@Composable
private fun CustomCalendarView(onDateSelected: (Date) -> Unit) {
    // Adds view to Compose
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            CalendarView(ContextThemeWrapper(context, R.style.CalenderViewCustom))
                .apply {
                    setDate(Constants.DEFAULT_LAST_PERIOD_DATE)
                }
        },
        update = { view ->
            view.minDate = Calendar.getInstance(Locale.CHINA).apply {
                set(2021, 1, 1)
            }.timeInMillis

            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                onDateSelected(
                    Date(Calendar.getInstance().apply {
                        set(year, month, dayOfMonth, 0, 0, 0)
                    }.timeInMillis)
                )
            }
        }
    )
}

@Preview("date picker")
@Composable
fun previewDatePicker() {
    WeComposeTheme {
        dialogContent({}) {}
    }
}