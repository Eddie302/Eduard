package com.example.amphsesviewer

import android.app.Application
import com.example.amphsesviewer.data.di.DataComponentManager
import com.example.amphsesviewer.feature.di.FeatureComponentManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        DataComponentManager.initComponent(this)
        FeatureComponentManager.initComponent(this)
    }
}