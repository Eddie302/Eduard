package com.example.amphsesviewer.feature.gallery.di

import com.example.amphsesviewer.domain.repository.IGalleryRepository
import com.example.amphsesviewer.feature.gallery.domain.GalleryInteractor
import com.example.amphsesviewer.feature.gallery.factory.GalleryViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class GalleryModule {

    @Provides
    fun providerFactory(
        galleryRepository: IGalleryRepository
    ): GalleryViewModelFactory {
        return GalleryViewModelFactory(
            Provider { GalleryInteractor(galleryRepository) }
        )
    }
}