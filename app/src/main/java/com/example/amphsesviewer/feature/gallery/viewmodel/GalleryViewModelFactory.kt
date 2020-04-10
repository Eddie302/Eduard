package com.example.amphsesviewer.feature.gallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amphsesviewer.feature.gallery.interactor.IGalleryInteractor
import javax.inject.Inject

class GalleryViewModelFactory @Inject constructor(private val interactor: IGalleryInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GalleryViewModel(
            interactor
        ) as T
    }
}