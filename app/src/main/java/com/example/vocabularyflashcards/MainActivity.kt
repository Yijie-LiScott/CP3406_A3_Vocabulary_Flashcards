package com.example.vocabularyflashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vocabularyflashcards.navigation.AppNavigation
import com.example.vocabularyflashcards.ui.theme.VocabularyFlashcardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VocabularyFlashcardsTheme {
                AppNavigation()
            }
        }
    }
}