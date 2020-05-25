package com.example.amphsesviewer.feature.album.viewmodel

import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState

enum class AlbumMode {
    View,
    Edit
}

sealed class AlbumEvent: ViewEvent {
    object SetEditMode: AlbumEvent()
    object SetViewMode: AlbumEvent()
    data class ViewCreated(val imageIds: List<Long>): AlbumEvent()
}

sealed class AlbumAction: ViewAction

data class AlbumState(
    val mode: AlbumMode
): ViewState


class AlbumViewModel(
    initState: AlbumState = AlbumState(AlbumMode.View)
): ViewModelBase<AlbumState, AlbumAction, AlbumEvent>(initState) {


    override fun invoke(event: AlbumEvent) {
        when(event) {
            is AlbumEvent.SetEditMode -> { sendNewState { copy(mode = AlbumMode.Edit) }}
            is AlbumEvent.SetViewMode -> { sendNewState { copy(mode = AlbumMode.View) }}
        }
    }
}