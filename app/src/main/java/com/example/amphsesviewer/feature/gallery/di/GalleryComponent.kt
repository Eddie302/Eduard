package com.example.amphsesviewer.feature.gallery.di

import com.example.amphsesviewer.data.di.DataComponentManager
import com.example.amphsesviewer.data.di.DataDependency
import com.example.amphsesviewer.feature.di.FeatureCoreComponentManager
import com.example.amphsesviewer.feature.di.FeatureCoreDependency
import com.example.amphsesviewer.ui.gallery.GalleryFragment
import dagger.Component


@Component(modules = [GalleryModule::class], dependencies = [DataDependency::class, FeatureCoreDependency::class])
interface GalleryComponent {

    fun inject(galleryFragment: GalleryFragment)

    companion object {
        fun initializer(galleryFragment: GalleryFragment) {
            DaggerGalleryComponent.builder()
                .dataDependency(DataComponentManager.component)
                .featureCoreDependency(FeatureCoreComponentManager.component)

        }
    }
}