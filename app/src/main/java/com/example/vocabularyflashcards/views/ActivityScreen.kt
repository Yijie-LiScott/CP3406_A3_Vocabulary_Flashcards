package com.example.vocabularyflashcards.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActivityScreen(
    onExitStudy: () -> Unit,
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
            text = "Flashcard Study Session",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Flashcard interaction will go here.")
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedButton(
            onClick = onExitStudy,
            modifier = Modifier.height(48.dp)
        ) {
            Text(text = "Exit Session")
        }
    }
}