package com.lolilicker.wanjetpackcompose

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Hatching : Screen("hatching")
    object Infant : Screen("infant")
    object Player : Screen("player")
}
