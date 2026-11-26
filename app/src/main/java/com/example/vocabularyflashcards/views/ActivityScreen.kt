package com.example.vocabularyflashcards.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vocabularyflashcards.data.WordEntity
import com.example.vocabularyflashcards.viewmodel.VocabUiState

@Composable
fun ActivityScreen(
    uiState: VocabUiState,
    onRecordReview: (WordEntity, Boolean) -> Unit,
    onExitStudy: () -> Unit,
    modifier: Modifier = Modifier
) {
    val words = uiState.words
    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    if (words.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No words available. Add some on the Home screen!")
        }
        return
    }

    val safeIndex = currentIndex % words.size
    val currentWord = words[safeIndex]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Card ${safeIndex + 1} of ${words.size}",
            style = MaterialTheme.typography.titleMedium
        )

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable { isFlipped = !isFlipped }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isFlipped) {
                    Text(
                        text = currentWord.word,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center
                    )
                    if (currentWord.phonetic.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentWord.phonetic,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Tap card to reveal definition",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                } else {
                    Text(
                        text = "${currentWord.word} (${currentWord.partOfSpeech})",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = currentWord.definition,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    if (uiState.showExamples && currentWord.example.isNotBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "\"${currentWord.example}\"",
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = {
                    onRecordReview(currentWord, false)
                    isFlipped = false
                    currentIndex = (safeIndex + 1) % words.size
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text("Need Review")
            }
            Button(
                onClick = {
                    onRecordReview(currentWord, true)
                    isFlipped = false
                    currentIndex = (safeIndex + 1) % words.size
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text("I Know This")
            }
        }

        TextButton(onClick = onExitStudy) {
            Text("Exit Session")
        }
    }
}