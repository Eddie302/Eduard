package com.example.amphsesviewer.feature.di

import com.example.amphsesviewer.domain.repository.IGalleryRepository
import com.example.amphsesviewer.feature.gallery.interactor.GalleryInteractor
import com.example.amphsesviewer.feature.gallery.interactor.IGalleryInteractor
import com.example.amphsesviewer.feature.imageviewer.interactor.IImageViewerInteractor
import com.example.amphsesviewer.feature.imageviewer.interactor.ImageViewerInteractor
import com.example.amphsesviewer.feature.loadimage.interactor.ILoadImageInteractor
import com.example.amphsesviewer.feature.loadimage.interactor.LoadImageInteractor
import dagger.Module
import dagger.Provides

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

    @Provides
    fun provideImageViewerFeature(repository: IGalleryRepository) : IImageViewerInteractor {
        return ImageViewerInteractor(repository)
    }
}