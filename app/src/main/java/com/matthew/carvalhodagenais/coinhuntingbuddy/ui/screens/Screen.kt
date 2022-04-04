package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

sealed class Screen(val route: String) {
    object Hunts: Screen(route = "hunts_screen") // Home Screen
    object Finds: Screen(route = "finds_screen")
    object About: Screen(route = "about_screen")
    object Settings: Screen(route = "settings_screen")
}
