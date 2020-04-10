package com.example.amphsesviewer.feature.di

import com.example.amphsesviewer.domain.repository.IGalleryRepository
import com.example.amphsesviewer.feature.gallery.domain.GalleryInteractor
import com.example.amphsesviewer.feature.gallery.domain.IGalleryInteractor
import com.example.amphsesviewer.feature.gallery.factory.GalleryViewModelFactory
import com.example.amphsesviewer.feature.loadimage.domain.ILoadImageInteractor
import com.example.amphsesviewer.feature.loadimage.domain.LoadImageInteractor
import com.example.amphsesviewer.feature.loadimage.factory.LoadImageViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FeatureModule {

    @Provides
    fun provideGalleryFeature(repository: IGalleryRepository) : IGalleryInteractor {
        return GalleryInteractor(repository)
    }

    @Provides
    fun provideLoadImageFeature(repository: IGalleryRepository) : ILoadImageInteractor {
        return LoadImageInteractor(repository)
    }
}