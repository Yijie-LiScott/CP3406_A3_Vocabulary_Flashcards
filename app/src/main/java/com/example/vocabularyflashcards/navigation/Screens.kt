package com.example.vocabularyflashcards.navigation

sealed class Screens(val route: String, val title: String) {
    data object Landing : Screens("landing", "Home")
    data object Activity : Screens("activity", "Study")
    data object Statistics : Screens("statistics", "Stats")
    data object Settings : Screens("settings", "Settings")
}