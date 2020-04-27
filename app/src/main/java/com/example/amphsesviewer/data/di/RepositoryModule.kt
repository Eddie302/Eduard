package com.example.amphsesviewer.data.di

import com.example.amphsesviewer.data.datasource.interfaces.IAlbumsDbSource
import com.example.amphsesviewer.data.datasource.interfaces.IImageDataDbSource
import com.example.amphsesviewer.data.datasource.interfaces.IInternalStorageDataSource
import com.example.amphsesviewer.data.repository.AlbumsRepository
import com.example.amphsesviewer.data.repository.ImageRepository
import com.example.amphsesviewer.domain.repository.IAlbumsRepository
import com.example.amphsesviewer.domain.repository.IImageRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideImageRepository(imageDataDbSource: IImageDataDbSource, internalStorageDataSource: IInternalStorageDataSource): IImageRepository {
        return ImageRepository(imageDataDbSource, internalStorageDataSource)
    }

    @Singleton
    @Provides
    fun provideAlbumsRepository(albumsDbSource: IAlbumsDbSource): IAlbumsRepository {
        return AlbumsRepository(albumsDbSource)
    }
}