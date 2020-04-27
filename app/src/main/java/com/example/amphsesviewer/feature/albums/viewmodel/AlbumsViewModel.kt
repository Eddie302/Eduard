package com.example.amphsesviewer.feature.albums.viewmodel

import com.example.amphsesviewer.domain.model.Album
import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import com.example.amphsesviewer.feature.albums.interactor.IAlbumsInteractor

sealed class AlbumsEvent: ViewEvent {
    object NewAlbumClicked: AlbumsEvent()
}

sealed class AlbumsAction: ViewAction {
    data class ShowError(val t: Throwable): AlbumsAction()
    object OpenNewAlbumCreator: AlbumsAction()
}

class AlbumsState(
    val albums: List<Album> = ArrayList()
): ViewState

class AlbumsViewModel(
    private val interactor: IAlbumsInteractor,
    initState: AlbumsState = AlbumsState()
) : ViewModelBase<AlbumsState, AlbumsAction, AlbumsEvent>(initState) {

    init {

    }

    override fun invoke(event: AlbumsEvent) {
        when (event) {
            is AlbumsEvent.NewAlbumClicked -> AlbumsAction.OpenNewAlbumCreator
        }
    }
}