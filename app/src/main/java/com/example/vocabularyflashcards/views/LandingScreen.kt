package com.example.vocabularyflashcards.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LandingScreen(
    onStartStudy: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Vocabulary Flashcards",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Ready to review your academic vocabulary?",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onStartStudy,
            modifier = Modifier.height(48.dp)
        ) {
            Text(text = "Start Studying")
        }
    }
}