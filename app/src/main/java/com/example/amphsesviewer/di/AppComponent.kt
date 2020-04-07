package com.example.amphsesviewer.di

import com.example.amphsesviewer.MainActivity
import com.example.amphsesviewer.data.di.DataDependency
import com.example.amphsesviewer.feature.di.FeatureDependency
import com.example.amphsesviewer.ui.gallery.GalleryFragment
import com.example.amphsesviewer.ui.loadimage.LoadImageFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
}