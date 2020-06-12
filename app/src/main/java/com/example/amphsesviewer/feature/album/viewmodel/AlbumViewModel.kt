package com.example.amphsesviewer.feature.album.viewmodel

import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import com.example.amphsesviewer.feature.albums.interactor.IAlbumsInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

enum class AlbumMode {
    View,
    Edit
}

sealed class AlbumEvent: ViewEvent {
    data class GalleryItemLongClicked(val position: Int): AlbumEvent()
    object SetEditMode: AlbumEvent()
    object SetViewMode: AlbumEvent()
    data class SaveToAlbumClicked(val albumId: Long, val newImageIds: List<Long>): AlbumEvent()
    data class DeleteImagesClicked(val imageIds: List<Long>): AlbumEvent()
    data class ViewCreated(val imageIds: List<Long>?): AlbumEvent()
    object ImagesLoaded: AlbumEvent()
}

sealed class AlbumAction: ViewAction {
    object GoBack: AlbumAction()
    data class SetCheckedImages(val imageIds: List<Long>?): AlbumAction()
    data class SetItemClickHandlers(val isAlbum: Boolean): AlbumAction()
    data class DeleteImages(val imageIds: List<Long>): AlbumAction()
}

data class AlbumState(
    val mode: AlbumMode,
    val imageIds: List<Long>? = emptyList()
): ViewState

class AlbumViewModel(
    private val interactor: IAlbumsInteractor,
    initState: AlbumState = AlbumState(AlbumMode.View)
): ViewModelBase<AlbumState, AlbumAction, AlbumEvent>(initState) {

    private fun saveAlbumChanges(albumId: Long, newImageIds: List<Long>) {
        viewState.value?.imageIds?.let { oldImageIds ->
            interactor.saveAlbumChanges(albumId, oldImageIds, newImageIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = { sendAction(AlbumAction.GoBack) },
                    onError = {}
                )
        }
    }

    override fun invoke(event: AlbumEvent) {
        when(event) {
            is AlbumEvent.SetEditMode -> { sendNewState { copy(mode = AlbumMode.Edit) }}
            is AlbumEvent.SetViewMode -> { sendNewState { copy(mode = AlbumMode.View) }}
            is AlbumEvent.SaveToAlbumClicked -> { saveAlbumChanges(event.albumId, event.newImageIds) }
            is AlbumEvent.ViewCreated -> { sendNewState { copy(imageIds = event.imageIds) }}
            is AlbumEvent.ImagesLoaded -> { sendAction(AlbumAction.SetCheckedImages(viewState.value?.imageIds)) }
            is AlbumEvent.DeleteImagesClicked -> { sendAction(AlbumAction.DeleteImages(event.imageIds)) }
        }
    }
}