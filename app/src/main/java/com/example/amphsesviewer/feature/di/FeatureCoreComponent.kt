package com.example.amphsesviewer.feature.di

import com.example.amphsesviewer.di.AppModule
import dagger.Component
import javax.inject.Singleton

interface FeatureCoreDependency

@Singleton
@Component(modules = [AppModule::class])
interface FeatureCoreComponent :
    FeatureCoreDependency