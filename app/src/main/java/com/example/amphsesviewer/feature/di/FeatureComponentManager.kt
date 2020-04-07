package com.example.amphsesviewer.feature.di

import com.example.amphsesviewer.App
import com.example.amphsesviewer.data.di.DataComponent
import com.example.amphsesviewer.data.di.DataDependency
import com.example.amphsesviewer.di.AppModule

object FeatureComponentManager {
    lateinit var component: FeatureDependency
        private set
    internal fun initComponent(app: App) {
        component = FeatureComponent.build(app)
    }
}