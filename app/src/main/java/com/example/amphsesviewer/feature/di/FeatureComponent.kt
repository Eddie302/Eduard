package com.example.amphsesviewer.feature.di

import android.content.Context
import com.example.amphsesviewer.data.di.DataComponent
import com.example.amphsesviewer.data.di.DataDependency
import com.example.amphsesviewer.di.AppModule
import com.example.amphsesviewer.feature.gallery.domain.IGalleryInteractor
import com.example.amphsesviewer.feature.loadimage.domain.ILoadImageInteractor
import com.example.amphsesviewer.ui.gallery.GalleryFragment
import com.example.amphsesviewer.ui.loadimage.LoadImageFragment
import dagger.Component
import javax.inject.Singleton

interface FeatureDependency {
    val galleryInteractor: IGalleryInteractor
    val loadImageInteractor: ILoadImageInteractor

    fun inject(galleryFragment: GalleryFragment)
    fun inject(loadImageFragment: LoadImageFragment)
}

@Singleton
@Component(modules = [FeatureModule::class], dependencies = [DataDependency::class])
interface FeatureComponent : FeatureDependency {


    companion object {
        fun build(context: Context): FeatureComponent {
            return DaggerFeatureComponent.builder()
                .dataDependency(DataComponent.build(context))
                .featureModule(FeatureModule())
                .build()
        }
    }




}