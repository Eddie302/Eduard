package com.example.amphsesviewer.data.di

import android.content.Context
import com.example.amphsesviewer.di.AppModule
import com.example.amphsesviewer.domain.repository.IImageRepository
import dagger.Component
import javax.inject.Singleton

interface DataDependency {
    val imageRepository: IImageRepository
}

@Singleton
@Component(modules = [RepositoryModule::class, StorageModule::class, AppModule::class])
interface DataComponent : DataDependency {

    companion object {

        fun build(context: Context): DataComponent {
            return DaggerDataComponent.builder()
                .repositoryModule(RepositoryModule())
                .storageModule(StorageModule())
                .appModule(AppModule(context.applicationContext))
                .build()
        }
    }
}

