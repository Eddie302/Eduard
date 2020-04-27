package com.example.amphsesviewer.feature.di

import com.example.amphsesviewer.domain.repository.IAlbumsRepository
import com.example.amphsesviewer.domain.repository.IImageRepository
import com.example.amphsesviewer.feature.albums.interactor.AlbumsInteractor
import com.example.amphsesviewer.feature.albums.interactor.IAlbumsInteractor
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
    fun provideGalleryFeature(repository: IImageRepository) : IGalleryInteractor {
        return GalleryInteractor(repository)
    }

    @Provides
    fun provideLoadImageFeature(repository: IImageRepository) : ILoadImageInteractor {
        return LoadImageInteractor(repository)
    }

    @Provides
    fun provideImageViewerFeature(repository: IImageRepository) : IImageViewerInteractor {
        return ImageViewerInteractor(repository)
    }

    @Provides
    fun provideAlbumsFeature(repository: IAlbumsRepository) : IAlbumsInteractor {
        return AlbumsInteractor(repository)
    }
}