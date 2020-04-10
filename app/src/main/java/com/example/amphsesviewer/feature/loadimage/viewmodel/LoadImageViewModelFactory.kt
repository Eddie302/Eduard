package com.example.amphsesviewer.feature.loadimage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amphsesviewer.feature.loadimage.interactor.ILoadImageInteractor
import javax.inject.Inject

class LoadImageViewModelFactory @Inject constructor(private val interactor: ILoadImageInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoadImageViewModel(interactor) as T
    }
}