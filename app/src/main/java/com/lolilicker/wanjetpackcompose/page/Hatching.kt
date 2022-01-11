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
import com.lolilicker.wanjetpackcompose.WanViewModel
import com.lolilicker.wanjetpackcompose.utils.DateUtils
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
        val hatchingViewModel: HatchingViewModel = viewModel()
        val wanViewModel: WanViewModel = viewModel()

        if (wanViewModel.lastPeriodDate.value == null) {
            listItemButton(
                "最后一次大姨妈哪天来哒？",
                fontSize = MaterialTheme.typography.h5.fontSize
            ) {
                hatchingViewModel.showDatePicker = true
            }
        } else {
            listItemButton(
                wanViewModel.grownTimeString,
                fontSize = MaterialTheme.typography.h4.fontSize
            )
            listItemButton(
                wanViewModel.dueTimeString,
                fontSize = MaterialTheme.typography.h4.fontSize
            )
        }

    }
}

@Composable
private fun datePickerView() {
    val hatchingViewModel: HatchingViewModel = viewModel()
    val wanViewModel: WanViewModel = viewModel()
    DatePicker(onDateSelected = {
        hatchingViewModel.showDatePicker = false
        wanViewModel.lastPeriodDate.value = it
        wanViewModel.recalculateDate()
    }) {
        hatchingViewModel.showDatePicker = false
    }
}

class HatchingViewModel : ViewModel() {
    var showDatePicker by mutableStateOf(false)
}

@Preview("preview")
@Composable
fun preview() {
    contentView()
}