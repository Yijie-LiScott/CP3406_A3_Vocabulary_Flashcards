package com.example.vocabularyflashcards

import android.app.Application
import com.example.vocabularyflashcards.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VocabApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VocabApplication)
            modules(appModule)
        }
    }
}