package com.example.amphsesviewer.data.di

import com.example.amphsesviewer.App

object DataComponentManager {
    lateinit var component: DataDependency
        private set

    fun initComponent(app: App) {
        component = DataComponent.initializer(app)
    }

}