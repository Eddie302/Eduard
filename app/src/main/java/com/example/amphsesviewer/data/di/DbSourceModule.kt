package com.example.amphsesviewer.data.di

import com.example.amphsesviewer.data.datasource.AlbumsDbSource
import com.example.amphsesviewer.data.datasource.ImageDataDbSource
import com.example.amphsesviewer.data.datasource.interfaces.IAlbumsDbSource
import com.example.amphsesviewer.data.datasource.interfaces.IImageDataDbSource
import com.example.amphsesviewer.data.db.DatabaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbSourceModule {

    @Provides
    @Singleton
    fun provideAlbumsDbSource(databaseStorage: DatabaseStorage): IAlbumsDbSource {
        return AlbumsDbSource(databaseStorage)
    }

    @Provides
    @Singleton
    fun provideImageDataDbSource(databaseStorage: DatabaseStorage): IImageDataDbSource {
        return ImageDataDbSource(databaseStorage)
    }
}