package com.example.amphsesviewer.feature.loadimage.di

import com.example.amphsesviewer.domain.repository.IGalleryRepository
import com.example.amphsesviewer.feature.loadimage.domain.LoadImageInteractor
import com.example.amphsesviewer.feature.loadimage.factory.LoadImageViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class LoadImageModule {

    @Provides
    fun providerFactory(
        galleryRepository: IGalleryRepository
    ): LoadImageViewModelFactory {
        return LoadImageViewModelFactory(
            Provider { LoadImageInteractor(galleryRepository) }
        )
    }
}