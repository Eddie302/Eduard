package com.example.amphsesviewer.feature.loadimage.di

import com.example.amphsesviewer.data.di.DataDependency
import com.example.amphsesviewer.feature.di.FeatureCoreDependency
import com.example.amphsesviewer.ui.loadimage.LoadImageFragment
import dagger.Component

@Component(modules = [LoadImageModule::class], dependencies = [DataDependency::class, FeatureCoreDependency::class])
interface LoadImageComponent {

    fun inject(loadImageFragment: LoadImageFragment)

    companion object {
        fun initializer(loadImageFragment: LoadImageFragment) {

        }
    }
}