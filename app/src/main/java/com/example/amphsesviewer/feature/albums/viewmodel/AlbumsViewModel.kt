package com.example.amphsesviewer.feature.albums.viewmodel

import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import com.example.amphsesviewer.feature.albums.interactor.IAlbumsInteractor

sealed class AlbumsEvent: ViewEvent

sealed class AlbumsAction: ViewAction

class AlbumsState(): ViewState

class AlbumsViewModel(
    private val interactor: IAlbumsInteractor,
    initState: AlbumsState = AlbumsState()
) : ViewModelBase<AlbumsState, AlbumsAction, AlbumsEvent>(initState) {



    override fun invoke(event: AlbumsEvent) {
        TODO("Not yet implemented")
    }
}