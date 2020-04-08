package com.example.amphsesviewer.data.di

import android.content.Context
import com.example.amphsesviewer.data.db.DatabaseStorage
import com.example.amphsesviewer.data.repository.GalleryRepository
import com.example.amphsesviewer.domain.repository.IGalleryRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideGalleryRepository(databaseStorage: DatabaseStorage, context: Context): IGalleryRepository {
        return GalleryRepository(databaseStorage, context)
    }
}