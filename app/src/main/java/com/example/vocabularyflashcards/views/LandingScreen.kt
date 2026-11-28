package com.example.vocabularyflashcards.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vocabularyflashcards.viewmodel.VocabUiState

@Composable
fun LandingScreen(
    uiState: VocabUiState,
    onSearchWord: (String) -> Unit,
    onStartStudy: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val totalWords = uiState.words.size
    val masteredCount = uiState.words.count { it.isMastered }
    val masteryProgress = if (totalWords > 0) masteredCount.toFloat() / totalWords.toFloat() else 0f

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Vocabulary Flashcards",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Deck Progress",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "$masteredCount / $totalWords Mastered",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                LinearProgressIndicator(
                    progress = { masteryProgress },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Daily Goal: ${uiState.dailyGoal} words",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Add a new English word (e.g. eloquent)") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (searchQuery.isNotBlank()) {
                    onSearchWord(searchQuery)
                    searchQuery = ""
                }
            },
            enabled = !uiState.isLoading && searchQuery.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Look Up & Save Word")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onStartStudy,
            enabled = uiState.words.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text(
                text = "Start Studying ($totalWords words)",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}