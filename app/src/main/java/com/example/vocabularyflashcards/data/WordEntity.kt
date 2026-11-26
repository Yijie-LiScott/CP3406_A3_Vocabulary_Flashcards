package com.example.vocabularyflashcards.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey val word: String,
    val phonetic: String,
    val partOfSpeech: String,
    val definition: String,
    val example: String,
    val reviewCount: Int = 0,
    val masteredCount: Int = 0,
    val isMastered: Boolean = false
)
