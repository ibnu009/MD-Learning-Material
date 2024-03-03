package com.ibnu.gemriawithcompose.ui.navigation

sealed class ScreenRoute(val route: String) {
    object Splash: ScreenRoute("splash")
    object Home: ScreenRoute("home")
    object Detail: ScreenRoute("detail/{id}"){
        fun createRoute(id: Int) = "detail/$id"
    }
    object Cart: ScreenRoute("cart")
    object Profile: ScreenRoute("profile")
}