package com.example.amphsesviewer.data.di

import com.example.amphsesviewer.data.repository.GalleryRepository
import com.example.amphsesviewer.domain.repository.IGalleryRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindGalleryRepository(
        galleryRepository: GalleryRepository
    ): IGalleryRepository
}