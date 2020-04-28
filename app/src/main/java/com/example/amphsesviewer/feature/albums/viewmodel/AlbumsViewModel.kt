package com.example.amphsesviewer.feature.albums.viewmodel

import com.example.amphsesviewer.domain.model.Album
import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import com.example.amphsesviewer.feature.albums.interactor.IAlbumsInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

sealed class AlbumsEvent: ViewEvent {
    object NewAlbumClicked: AlbumsEvent()
    data class CreateNewAlbumClicked(val name: String): AlbumsEvent()
}

sealed class AlbumsAction: ViewAction {
    data class ShowError(val t: Throwable): AlbumsAction()
    object OpenNewAlbumCreator: AlbumsAction()
}

data class AlbumsState(
    val albums: List<Album> = ArrayList()
): ViewState

class AlbumsViewModel(
    private val interactor: IAlbumsInteractor,
    initState: AlbumsState = AlbumsState()
) : ViewModelBase<AlbumsState, AlbumsAction, AlbumsEvent>(initState) {

    init {
        interactor.loadAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { sendNewState { copy(albums = it) } },
                onError = { AlbumsAction.ShowError(it) }
            )
    }

    private fun createAlbum(name: String) {
        interactor.createAlbum(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { AlbumsAction.ShowError(it) }
            )
    }

    override fun invoke(event: AlbumsEvent) {
        when (event) {
            is AlbumsEvent.NewAlbumClicked -> sendAction(AlbumsAction.OpenNewAlbumCreator)
            is AlbumsEvent.CreateNewAlbumClicked -> createAlbum(event.name)
        }
    }
}