package com.example.amphsesviewer.data.di

import android.content.Context
import androidx.room.Room
import com.example.amphsesviewer.data.db.DatabaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): DatabaseStorage {
        return Room
            .databaseBuilder(context, DatabaseStorage::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }
}