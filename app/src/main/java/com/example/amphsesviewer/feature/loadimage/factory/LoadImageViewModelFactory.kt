package com.example.amphsesviewer.feature.loadimage.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amphsesviewer.feature.loadimage.viewmodel.LoadImageViewModel
import com.example.amphsesviewer.feature.loadimage.domain.LoadImageInteractor
import javax.inject.Provider

class LoadImageViewModelFactory(private val interactor: Provider<LoadImageInteractor>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoadImageViewModel(interactor.get()) as T
    }
}