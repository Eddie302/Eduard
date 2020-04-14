package com.example.amphsesviewer.feature.imageviewer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amphsesviewer.feature.imageviewer.interactor.IImageViewerInteractor
import javax.inject.Inject

class ImageViewerViewModelFactory @Inject constructor(private val interactor: IImageViewerInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageViewerViewModel(interactor) as T
    }
}