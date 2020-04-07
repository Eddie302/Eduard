package com.example.amphsesviewer.data.di

import android.content.Context
import com.example.amphsesviewer.di.AppModule
import com.example.amphsesviewer.domain.repository.IGalleryRepository
import dagger.Component
import javax.inject.Singleton

interface DataDependency {
    val galleryRepository: IGalleryRepository
}

@Singleton
@Component(modules = [RepositoryModule::class, AppModule::class])
interface DataComponent : DataDependency {

    companion object {

        fun initializer(context: Context): DataComponent {
            return DaggerDataComponent.builder()
                .appModule(AppModule(context.applicationContext))
                .build()
        }
    }
}

