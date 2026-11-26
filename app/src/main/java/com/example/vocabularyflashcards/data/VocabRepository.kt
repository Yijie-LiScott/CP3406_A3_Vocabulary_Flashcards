package com.example.vocabularyflashcards.data

import kotlinx.coroutines.flow.Flow

interface VocabRepository {
    val allWords: Flow<List<WordEntity>>
    suspend fun fetchAndSaveWord(query: String): Result<WordEntity>
    suspend fun recordReview(word: WordEntity, isKnown: Boolean)
    suspend fun seedInitialWordsIfNeeded()
    suspend fun resetAllData()
}

class VocabRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: VocabDao
) : VocabRepository {

    override val allWords: Flow<List<WordEntity>> = dao.getAllWords()

    override suspend fun fetchAndSaveWord(query: String): Result<WordEntity> {
        return try {
            val cleaned = query.trim().lowercase()
            if (cleaned.isEmpty()) return Result.failure(IllegalArgumentException("Please enter a word."))

            val response = api.getWord(cleaned)
            val dto = response.firstOrNull()
                ?: return Result.failure(Exception("Word not found."))

            val phoneticText = dto.phonetic
                ?: dto.phonetics.firstOrNull { !it.text.isNullOrBlank() }?.text
                ?: ""
            val firstMeaning = dto.meanings.firstOrNull()
            val firstDef = firstMeaning?.definitions?.firstOrNull()

            val entity = WordEntity(
                word = dto.word.replaceFirstChar { it.uppercase() },
                phonetic = phoneticText,
                partOfSpeech = firstMeaning?.partOfSpeech ?: "n.",
                definition = firstDef?.definition ?: "No definition available.",
                example = firstDef?.example ?: "No example sentence available."
            )
            dao.insertWord(entity)
            Result.success(entity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun recordReview(word: WordEntity, isKnown: Boolean) {
        val newReviewCount = word.reviewCount + 1
        val newMasteredCount = if (isKnown) word.masteredCount + 1 else word.masteredCount
        val updated = word.copy(
            reviewCount = newReviewCount,
            masteredCount = newMasteredCount,
            isMastered = isKnown
        )
        dao.updateWord(updated)
    }

    override suspend fun seedInitialWordsIfNeeded() {
        if (dao.getWordCount() == 0) {
            val seedWords = listOf(
                WordEntity(
                    word = "Ubiquitous",
                    phonetic = "/juːˈbɪk.wɪ.təs/",
                    partOfSpeech = "adjective",
                    definition = "Present, appearing, or found everywhere.",
                    example = "Smartphones have become ubiquitous in modern university classrooms."
                ),
                WordEntity(
                    word = "Pragmatic",
                    phonetic = "/præɡˈmæt.ɪk/",
                    partOfSpeech = "adjective",
                    definition = "Dealing with things sensibly and realistically.",
                    example = "She took a pragmatic approach to solving the software architecture issue."
                ),
                WordEntity(
                    word = "Ephemeral",
                    phonetic = "/ɪˈfem.ər.əl/",
                    partOfSpeech = "adjective",
                    definition = "Lasting for a very short time.",
                    example = "Trends on social media are often ephemeral."
                ),
                WordEntity(
                    word = "Mitigate",
                    phonetic = "/ˈmɪt.ɪ.ɡeɪt/",
                    partOfSpeech = "verb",
                    definition = "Make something bad less severe, serious, or painful.",
                    example = "Automated tests help mitigate the risk of production bugs."
                ),
                WordEntity(
                    word = "Empirical",
                    phonetic = "/ɪmˈpɪr.ɪ.kəl/",
                    partOfSpeech = "adjective",
                    definition = "Based on observation or experience rather than pure theory.",
                    example = "The paper provides empirical evidence supporting the new algorithm."
                )
            )
            dao.insertWords(seedWords)
        }
    }

    override suspend fun resetAllData() {
        dao.deleteAllWords()
        seedInitialWordsIfNeeded()
    }
}