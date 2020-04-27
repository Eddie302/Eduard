package com.example.amphsesviewer.data.di

import android.content.Context
import com.example.amphsesviewer.data.datasource.InternalStorageDataSource
import com.example.amphsesviewer.data.datasource.interfaces.IInternalStorageDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InternalStorageModule {

    @Provides
    @Singleton
    fun provideInternalStorage(context: Context): IInternalStorageDataSource {
        return InternalStorageDataSource(context)
    }
}