package com.example.vocabularyflashcards.data

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path

@Serializable
data class WordDto(
    val word: String,
    val phonetic: String? = null,
    val phonetics: List<PhoneticDto> = emptyList(),
    val meanings: List<MeaningDto> = emptyList()
)

@Serializable
data class PhoneticDto(
    val text: String? = null
)

@Serializable
data class MeaningDto(
    val partOfSpeech: String = "",
    val definitions: List<DefinitionDto> = emptyList()
)

@Serializable
data class DefinitionDto(
    val definition: String = "",
    val example: String? = null
)

interface DictionaryApi {
    @GET("entries/en/{word}")
    suspend fun getWord(@Path("word") word: String): List<WordDto>
}