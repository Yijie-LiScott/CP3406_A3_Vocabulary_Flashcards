package com.example.vocabularyflashcards.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
    val masteryRatio = if (totalWords > 0) masteredWords.toFloat() / totalWords.toFloat() else 0f

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Learning Progress",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatItem(label = "Words", value = "$totalWords")
                    StatItem(label = "Mastered", value = "$masteredWords")
                    StatItem(label = "Reviews", value = "$totalReviews")
                    StatItem(label = "Accuracy", value = "$accuracy%")
                }
                LinearProgressIndicator(
                    progress = { masteryRatio },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Text(
            text = "Word Breakdown",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
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
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = word.word,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Reviews: ${word.reviewCount} | Known: ${word.masteredCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        AssistChip(
                            onClick = {},
                            label = {
                                Text(if (word.isMastered) "Mastered" else "Learning")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}