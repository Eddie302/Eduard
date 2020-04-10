package com.example.amphsesviewer.feature.di

import com.example.amphsesviewer.feature.gallery.domain.IGalleryInteractor
import com.example.amphsesviewer.feature.gallery.factory.GalleryViewModelFactory
import com.example.amphsesviewer.feature.loadimage.domain.ILoadImageInteractor
import com.example.amphsesviewer.feature.loadimage.factory.LoadImageViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class FeatureModelProviderModule {
    @Provides
    fun provideGalleryViewModelFactory(interactor: IGalleryInteractor) : GalleryViewModelFactory {
        return GalleryViewModelFactory(interactor)
    }

    @Provides
    fun provideLoadImageViewModelFactory(interactor: ILoadImageInteractor) : LoadImageViewModelFactory {
        return LoadImageViewModelFactory(interactor)
    }
}