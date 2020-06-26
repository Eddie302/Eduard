package com.example.amphsesviewer.data.di

import com.example.amphsesviewer.data.datasource.AlbumsDbSource
import com.example.amphsesviewer.data.datasource.AuthFirebaseSource
import com.example.amphsesviewer.data.datasource.ImageDataDbSource
import com.example.amphsesviewer.data.datasource.UserFirestoreSource
import com.example.amphsesviewer.data.datasource.interfaces.IAlbumsDbSource
import com.example.amphsesviewer.data.datasource.interfaces.IAuthFirebaseSource
import com.example.amphsesviewer.data.datasource.interfaces.IImageDataDbSource
import com.example.amphsesviewer.data.datasource.interfaces.IUserFirestoreSource
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

    @Provides
    @Singleton
    fun provideAuthFirebaseSource(): IAuthFirebaseSource {
        return AuthFirebaseSource()
    }

    @Provides
    @Singleton
    fun provideUserFirestoreSource(): IUserFirestoreSource {
        return UserFirestoreSource()
    }
}