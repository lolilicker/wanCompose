package com.lolilicker.wanjetpackcompose.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme


@Composable
fun Player(mediaItem: MediaItem) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val player = remember {
        val player = SimpleExoPlayer.Builder(context)
            .build()
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
        player
    }

    val playerView = remember {
        val playerView = PlayerView(context)
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                playerView.onResume()
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                playerView.onPause()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                player.release()
            }
        })
        playerView
    }

    DisposableEffect(LocalContext.current) {
        onDispose {
            player.release()
        }
    }

    AndroidView(
        factory = { playerView },
        modifier = Modifier
            .fillMaxWidth()
    ) { _ ->
        playerView.player = player
    }
}