package com.example.amphsesviewer.feature.di

import android.content.Context
import com.example.amphsesviewer.data.di.DataComponent
import com.example.amphsesviewer.data.di.DataDependency
import com.example.amphsesviewer.ui.gallery.GalleryFragment
import com.example.amphsesviewer.ui.imageviewer.ImageViewerFragment
import com.example.amphsesviewer.ui.loadimage.LoadImageFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FeatureModule::class, FeatureModelProviderModule::class], dependencies = [DataDependency::class])
interface FeatureComponent {
    fun inject(galleryFragment: GalleryFragment)
    fun inject(loadImageFragment: LoadImageFragment)
    fun inject(imageViewerFragment: ImageViewerFragment)


    companion object {
        fun build(context: Context): FeatureComponent {
            return DaggerFeatureComponent.builder()
                .dataDependency(DataComponent.build(context))
                .featureModule(FeatureModule())
                .build()
        }
    }




}