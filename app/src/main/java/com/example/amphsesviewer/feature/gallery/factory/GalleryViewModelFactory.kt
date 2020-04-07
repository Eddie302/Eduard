package com.example.amphsesviewer.feature.gallery.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amphsesviewer.feature.gallery.domain.GalleryInteractor
import com.example.amphsesviewer.feature.gallery.viewmodel.GalleryViewModel
import javax.inject.Provider

class GalleryViewModelFactory(private val interactor: Provider<GalleryInteractor>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GalleryViewModel(
            interactor.get()
        ) as T
    }
}