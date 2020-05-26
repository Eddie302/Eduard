package com.example.amphsesviewer.feature.album.viewmodel

import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import com.example.amphsesviewer.feature.albums.interactor.IAlbumsInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

enum class AlbumMode {
    View,
    Edit
}

sealed class AlbumEvent: ViewEvent {
    object SetEditMode: AlbumEvent()
    object SetViewMode: AlbumEvent()
    data class SaveImages(val albumId: Long, val imagesId: List<Long>): AlbumEvent()
    data class ViewCreated(val imageIds: List<Long>): AlbumEvent()
}

sealed class AlbumAction: ViewAction

data class AlbumState(
    val mode: AlbumMode
): ViewState


class AlbumViewModel(
    private val interactor: IAlbumsInteractor,
    initState: AlbumState = AlbumState(AlbumMode.View)
): ViewModelBase<AlbumState, AlbumAction, AlbumEvent>(initState) {


    private fun saveImages(albumId: Long, imagesId: List<Long>) {
        interactor.saveImages(albumId, imagesId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {},
                onError = {}
            )
    }

    override fun invoke(event: AlbumEvent) {
        when(event) {
            is AlbumEvent.SetEditMode -> { sendNewState { copy(mode = AlbumMode.Edit) }}
            is AlbumEvent.SetViewMode -> { sendNewState { copy(mode = AlbumMode.View) }}
            is AlbumEvent.SaveImages -> { saveImages(event.albumId, event.imagesId) }
        }
    }
}