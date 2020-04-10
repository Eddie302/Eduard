package com.example.amphsesviewer.feature.di

import com.example.amphsesviewer.feature.gallery.interactor.IGalleryInteractor
import com.example.amphsesviewer.feature.gallery.viewmodel.GalleryViewModelFactory
import com.example.amphsesviewer.feature.loadimage.interactor.ILoadImageInteractor
import com.example.amphsesviewer.feature.loadimage.viewmodel.LoadImageViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class FeatureModelProviderModule {
    @Provides
    fun provideGalleryViewModelFactory(interactor: IGalleryInteractor) : GalleryViewModelFactory {
        return GalleryViewModelFactory(
            interactor
        )
    }

    @Provides
    fun provideLoadImageViewModelFactory(interactor: ILoadImageInteractor) : LoadImageViewModelFactory {
        return LoadImageViewModelFactory(
            interactor
        )
    }
}