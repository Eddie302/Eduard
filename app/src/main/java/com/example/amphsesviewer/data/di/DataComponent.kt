package com.example.amphsesviewer.data.di

import android.content.Context
import com.example.amphsesviewer.di.AppModule
import com.example.amphsesviewer.domain.repository.IAlbumsRepository
import com.example.amphsesviewer.domain.repository.IAuthRepository
import com.example.amphsesviewer.domain.repository.IImageRepository
import dagger.Component
import javax.inject.Singleton

interface DataDependency {
    val imageRepository: IImageRepository
    val albumsRepository: IAlbumsRepository
    val authRepository: IAuthRepository
}

@Singleton
@Component(modules = [RepositoryModule::class, DatabaseModule::class, DbSourceModule::class, InternalStorageModule::class, AppModule::class])
interface DataComponent : DataDependency {

    companion object {

        fun build(context: Context): DataComponent {
            return DaggerDataComponent.builder()
                .repositoryModule(RepositoryModule())
                .databaseModule(DatabaseModule())
                .internalStorageModule(InternalStorageModule())
                .dbSourceModule(DbSourceModule())
                .appModule(AppModule(context.applicationContext))
                .build()
        }
    }
}

