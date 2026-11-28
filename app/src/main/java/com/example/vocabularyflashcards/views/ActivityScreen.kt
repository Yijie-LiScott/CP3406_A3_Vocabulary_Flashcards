package com.example.vocabularyflashcards.views

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
    val progress = (safeIndex + 1).toFloat() / words.size.toFloat()

    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing),
        label = "cardFlip"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Card ${safeIndex + 1} of ${words.size}",
                style = MaterialTheme.typography.titleMedium
            )
            SuggestionChip(
                onClick = {},
                label = { Text(if (currentWord.isMastered) "Mastered" else "Learning") }
            )
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )

        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = if (rotation <= 90f) {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8f * density
                }
                .clickable { isFlipped = !isFlipped }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .graphicsLayer {
                        if (rotation > 90f) rotationY = 180f
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (rotation <= 90f) {
                    Text(
                        text = currentWord.word,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    if (currentWord.phonetic.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentWord.phonetic,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Tap card to flip",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.outline
                    )
                } else {
                    Text(
                        text = "${currentWord.word} (${currentWord.partOfSpeech})",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = currentWord.definition,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    if (uiState.showExamples && currentWord.example.isNotBlank()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "\"${currentWord.example}\"",
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
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
                    .height(50.dp)
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
                    .height(50.dp)
            ) {
                Text("I Know This")
            }
        }

        TextButton(onClick = onExitStudy) {
            Text("Exit Session")
        }
    }
}