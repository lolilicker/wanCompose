package com.lolilicker.wanjetpackcompose.page

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.lolilicker.wanjetpackcompose.Constants
import com.lolilicker.wanjetpackcompose.Screen
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref
import com.lolilicker.wanjetpackcompose.storage.sharedpreferences.Pref.dataStore
import com.lolilicker.wanjetpackcompose.utils.DateUtils
import com.lolilicker.wanjetpackcompose.widget.Player
import com.lolilicker.wanjetpackcompose.widget.backPressHandler
import com.lolilicker.wanjetpackcompose.widget.listItemButton
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*


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
            playList(viewModel)
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
fun playList(viewModel: PlayerViewModel) {
    viewModel.initData(LocalContext.current)
    Column {
        playListItem(name = "唤醒", index = 0, viewModel = viewModel)
        playListItem(name = "巩固", index = 1, viewModel = viewModel)
        playListItem(name = "加强", index = 2, viewModel = viewModel)
    }
}

@Composable
fun playListItem(name: String, index: Int, viewModel: PlayerViewModel) {
    val context = LocalContext.current
    listItemButton(text = "$name(${viewModel.lastVideoTimeList[index]}) ->") {
        viewModel.playingIndex = index
        viewModel.saveLastSportTime(context, index = index)
    }
}

class PlayerViewModel() : ViewModel() {
    val NO_DATA = "今天还没做"
    var playingIndex by mutableStateOf<Int>(-1)
    var lastVideoTimeList = mutableStateListOf<String>(NO_DATA, NO_DATA, NO_DATA)

    fun initData(context: Context) {
        val result = mutableListOf<String>()
        Log.d("initData", "")
        mediaItemList.forEachIndexed { i, mediaItem ->
            Log.d("initData", "$i")
            viewModelScope.launch {
                context.dataStore.data.map {
                    it[longPreferencesKey(Pref.LAST_VIDEO_TIME(i))]
                }.collect {
                    Log.d("initData collect", "$it")
                    if (it != null && DateUtils.isSameDay(Date(it), Date())) {
                        lastVideoTimeList.set(index = i, DateUtils.formatPrettyDateTime(Date(it)))
                    } else {
                        lastVideoTimeList.set(index = i, NO_DATA)
                    }
                }
            }
        }
    }

    fun saveLastSportTime(context: Context, index: Int) {
        viewModelScope.launch {
            context.dataStore.edit {
                it[longPreferencesKey(Pref.LAST_VIDEO_TIME(index))] = System.currentTimeMillis()
            }
            initData(context = context)
        }
    }
}

