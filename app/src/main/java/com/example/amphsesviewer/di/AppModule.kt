package com.example.amphsesviewer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    internal fun provideAppContext(): Context {
        return context.applicationContext
    }
}