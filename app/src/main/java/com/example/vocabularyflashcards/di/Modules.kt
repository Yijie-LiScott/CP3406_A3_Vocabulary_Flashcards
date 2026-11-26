package com.example.vocabularyflashcards.di

import androidx.room.Room
import com.example.vocabularyflashcards.data.DictionaryApi
import com.example.vocabularyflashcards.data.VocabDatabase
import com.example.vocabularyflashcards.data.VocabRepository
import com.example.vocabularyflashcards.data.VocabRepositoryImpl
import com.example.vocabularyflashcards.viewmodel.VocabViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val appModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }

    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .addConverterFactory(get<Json>().asConverterFactory(contentType))
            .build()
    }

    single<DictionaryApi> {
        get<Retrofit>().create(DictionaryApi::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            VocabDatabase::class.java,
            "vocab_database"
        ).build()
    }

    single { get<VocabDatabase>().vocabDao() }

    single<VocabRepository> { VocabRepositoryImpl(get(), get()) }

    viewModel { VocabViewModel(get()) }
}