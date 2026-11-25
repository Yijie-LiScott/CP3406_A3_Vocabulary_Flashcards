package com.example.vocabularyflashcards.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "User Statistics",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Progress and accuracy metrics will appear here.")
    }
}