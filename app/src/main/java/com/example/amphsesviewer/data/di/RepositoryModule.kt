package com.example.amphsesviewer.data.di

import com.example.amphsesviewer.data.datasource.interfaces.*
import com.example.amphsesviewer.data.repository.AlbumsRepository
import com.example.amphsesviewer.data.repository.AuthRepository
import com.example.amphsesviewer.data.repository.ImageRepository
import com.example.amphsesviewer.domain.repository.IAlbumsRepository
import com.example.amphsesviewer.domain.repository.IAuthRepository
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

    @Singleton
    @Provides
    fun provideAuthRepository(authDataSource: IAuthFirebaseSource, userDataSource: IUserFirestoreSource): IAuthRepository {
        return AuthRepository(authDataSource, userDataSource)
    }
}