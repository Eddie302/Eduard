package com.example.amphsesviewer

import android.app.Application
import com.example.amphsesviewer.di.AppComponent
import com.example.amphsesviewer.di.AppModule
import com.example.amphsesviewer.data.di.DataComponentManager
import com.example.amphsesviewer.data.di.DataDependency
import com.example.amphsesviewer.di.DaggerAppComponent
import com.example.amphsesviewer.feature.di.FeatureComponentManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        DataComponentManager.initComponent(this)
        FeatureComponentManager.initComponent(this)
        component = buildComponent()
    }

    private fun buildComponent() : AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var component: AppComponent
    }

}