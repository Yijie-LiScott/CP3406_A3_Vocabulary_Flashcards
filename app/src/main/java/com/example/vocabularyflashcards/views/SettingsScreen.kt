package com.example.vocabularyflashcards.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vocabularyflashcards.viewmodel.VocabUiState

@Composable
fun SettingsScreen(
    uiState: VocabUiState,
    onToggleExamples: (Boolean) -> Unit,
    onUpdateDailyGoal: (Int) -> Unit,
    onResetData: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Preferences",
            style = MaterialTheme.typography.headlineSmall
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Show Example Sentences", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Display usage examples on the back of flashcards",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Switch(
                checked = uiState.showExamples,
                onCheckedChange = onToggleExamples
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Daily Study Goal: ${uiState.dailyGoal} words",
                style = MaterialTheme.typography.titleMedium
            )
            Slider(
                value = uiState.dailyGoal.toFloat(),
                onValueChange = { onUpdateDailyGoal(it.toInt()) },
                valueRange = 5f..30f,
                steps = 4
            )
        }

        HorizontalDivider()

        OutlinedButton(
            onClick = onResetData,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset Progress & Restore Default Words")
        }

        uiState.statusMessage?.let { msg ->
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}