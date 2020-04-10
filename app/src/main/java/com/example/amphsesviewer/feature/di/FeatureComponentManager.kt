package com.example.amphsesviewer.feature.di

import com.example.amphsesviewer.App

object FeatureComponentManager {
    lateinit var component: FeatureComponent
        private set
    internal fun initComponent(app: App) {
        component = FeatureComponent.build(app)
    }
}