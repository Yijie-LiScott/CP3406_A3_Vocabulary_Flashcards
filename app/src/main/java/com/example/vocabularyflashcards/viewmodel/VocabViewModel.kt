package com.example.vocabularyflashcards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularyflashcards.data.VocabRepository
import com.example.vocabularyflashcards.data.WordEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class VocabUiState(
    val words: List<WordEntity> = emptyList(),
    val isLoading: Boolean = false,
    val statusMessage: String? = null,
    val showExamples: Boolean = true,
    val dailyGoal: Int = 10
)

class VocabViewModel(
    private val repository: VocabRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VocabUiState())
    val uiState: StateFlow<VocabUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedInitialWordsIfNeeded()
        }
        viewModelScope.launch {
            repository.allWords.collect { wordList ->
                _uiState.update { it.copy(words = wordList) }
            }
        }
    }

    fun searchAndAddWord(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, statusMessage = null) }
            val result = repository.fetchAndSaveWord(query)
            result.onSuccess { addedWord ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        statusMessage = "Added '${addedWord.word}' to your deck!"
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        statusMessage = "Lookup failed: ${error.message ?: "Word not found"}"
                    )
                }
            }
        }
    }

    fun recordReview(word: WordEntity, isKnown: Boolean) {
        viewModelScope.launch {
            repository.recordReview(word, isKnown)
        }
    }

    fun toggleShowExamples(enabled: Boolean) {
        _uiState.update { it.copy(showExamples = enabled) }
    }

    fun updateDailyGoal(goal: Int) {
        _uiState.update { it.copy(dailyGoal = goal) }
    }

    fun resetAllData() {
        viewModelScope.launch {
            repository.resetAllData()
            _uiState.update { it.copy(statusMessage = "Progress reset to default words.") }
        }
    }

    fun clearMessage() {
        _uiState.update { it.copy(statusMessage = null) }
    }
}
