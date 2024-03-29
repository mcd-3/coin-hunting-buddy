package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

sealed class Screen(val route: String) {
    object Hunts: Screen(route = "hunts_screen") // Home Screen
    object Finds: Screen(route = "finds_screen")
    object About: Screen(route = "about_screen")
    object Settings: Screen(route = "settings_screen")
    object NewHunt: Screen(route = "new_hunt_screen")
    object Details: Screen(route = "details_screen")
    object FindDetails: Screen(route = "find_details_screen")
    object Hunt: Screen(route = "hunt_screen")
    object Review: Screen(route = "review_screen")
}
