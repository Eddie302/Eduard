package com.example.amphsesviewer.feature.gallery.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amphsesviewer.feature.gallery.domain.IGalleryInteractor
import com.example.amphsesviewer.feature.gallery.viewmodel.GalleryViewModel
import javax.inject.Inject
import javax.inject.Provider

class GalleryViewModelFactory @Inject constructor(private val interactor: IGalleryInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GalleryViewModel(
            interactor
        ) as T
    }
}