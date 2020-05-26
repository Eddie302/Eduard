package com.example.amphsesviewer.feature.album.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amphsesviewer.feature.albums.interactor.IAlbumsInteractor
import javax.inject.Inject

class AlbumViewModelFactory @Inject constructor(private val interactor: IAlbumsInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumViewModel(interactor) as T
    }
}