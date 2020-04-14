package com.example.amphsesviewer.data.di

import android.content.Context
import com.example.amphsesviewer.data.db.DatabaseStorage
import com.example.amphsesviewer.data.repository.ImageRepository
import com.example.amphsesviewer.domain.repository.IImageRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideImageRepository(databaseStorage: DatabaseStorage, context: Context): IImageRepository {
        return ImageRepository(databaseStorage, context)
    }
}