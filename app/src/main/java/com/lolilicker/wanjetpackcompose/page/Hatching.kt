package com.lolilicker.wanjetpackcompose.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lolilicker.wanjetpackcompose.widget.DatePicker
import com.lolilicker.wanjetpackcompose.widget.listItemButton
import java.util.*

@Composable
fun hatchingPage(navController: NavController) {
    val viewModel: HatchingViewModel = viewModel()
    contentView()

    if (viewModel.showDatePicker) {
        datePickerView()
    }
}

@Composable
private fun contentView() {
    Column(
        Modifier
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        val viewModel: HatchingViewModel = viewModel()
        listItemButton("最后一次大姨妈那天来哒", fontSize = MaterialTheme.typography.h5.fontSize) {
            viewModel.showDatePicker = true
        }
    }
}

@Composable
private fun datePickerView() {
    val viewModel: HatchingViewModel = viewModel()
    DatePicker(onDateSelected = {
        viewModel.showDatePicker = false
        viewModel.lastPeriodDate = it
    }) {
        viewModel.showDatePicker = false
    }
}

class HatchingViewModel : ViewModel() {
    var showDatePicker by mutableStateOf(false)
    var lastPeriodDate by mutableStateOf(Date())
}

@Preview("preview")
@Composable
fun preview() {
    contentView()
}