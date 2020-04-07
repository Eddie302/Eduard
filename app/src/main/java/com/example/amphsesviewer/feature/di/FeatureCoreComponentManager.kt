package com.example.amphsesviewer.feature.di

import com.example.amphsesviewer.App
import com.example.amphsesviewer.di.AppModule

object FeatureCoreComponentManager {
    lateinit var component: FeatureCoreDependency
        private set
    internal fun initComponent(app: App) {
        component =
            DaggerFeatureCoreComponent.builder()
                .appModule(AppModule(app.applicationContext))
                .build()
    }
}