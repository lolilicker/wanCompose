package com.lolilicker.wanjetpackcompose.page

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.lolilicker.wanjetpackcompose.Constants
import com.lolilicker.wanjetpackcompose.Screen
import com.lolilicker.wanjetpackcompose.widget.Player
import com.lolilicker.wanjetpackcompose.widget.backPressHandler
import com.lolilicker.wanjetpackcompose.widget.listItemButton
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme


private val mediaItemList = listOf(
    MediaItem.fromUri("${Constants.ASSETS_PATH}/videos/awake.mp4"),
    MediaItem.fromUri("${Constants.ASSETS_PATH}/videos/consolidate.mp4"),
    MediaItem.fromUri("${Constants.ASSETS_PATH}/videos/enhance.mp4")
)

@Composable
fun playerPage(navController: NavController) {
    val viewModel: PlayerViewModel = viewModel()
    WeComposeTheme {
        if (viewModel.playingIndex == -1) {
            playList()
        } else {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Player(mediaItemList[viewModel.playingIndex])
                backPressHandler(onBackPressed = {
                    viewModel.playingIndex = -1
                })
            }
        }
    }
}

@Composable
fun playList() {
    val viewModel: PlayerViewModel = viewModel()
    Column {
        listItemButton(text = "唤醒 ->") {
            viewModel.playingIndex = 0
        }
        listItemButton(text = "巩固 ->") {
            viewModel.playingIndex = 1
        }
        listItemButton(text = "加强 ->") {
            viewModel.playingIndex = 2
        }
    }
}

class PlayerViewModel() : ViewModel() {
    var playingIndex by mutableStateOf<Int>(-1)
}

