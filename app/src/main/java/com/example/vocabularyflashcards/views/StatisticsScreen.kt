package com.example.vocabularyflashcards.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vocabularyflashcards.viewmodel.VocabUiState

@Composable
fun StatisticsScreen(
    uiState: VocabUiState,
    modifier: Modifier = Modifier
) {
    val totalWords = uiState.words.size
    val masteredWords = uiState.words.count { it.isMastered }
    val totalReviews = uiState.words.sumOf { it.reviewCount }
    val totalKnown = uiState.words.sumOf { it.masteredCount }
    val accuracy = if (totalReviews > 0) (totalKnown * 100 / totalReviews) else 0

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Learning Progress",
            style = MaterialTheme.typography.headlineSmall
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Total Words: $totalWords", style = MaterialTheme.typography.titleMedium)
                Text("Mastered: $masteredWords / $totalWords")
                Text("Total Reviews: $totalReviews")
                Text("Accuracy: $accuracy%")
            }
        }

        Text(
            text = "Word Breakdown",
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.words) { word ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(word.word, style = MaterialTheme.typography.titleSmall)
                            Text(
                                text = "Reviews: ${word.reviewCount}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            text = if (word.isMastered) "Mastered" else "Learning",
                            color = if (word.isMastered) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            }
                        )
                    }
                }
            }
        }
    }
}