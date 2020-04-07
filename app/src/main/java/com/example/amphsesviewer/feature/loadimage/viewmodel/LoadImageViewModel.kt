package com.example.amphsesviewer.feature.loadimage.viewmodel

import android.graphics.Bitmap
import com.example.amphsesviewer.ui.core.ViewAction
import com.example.amphsesviewer.ui.core.ViewEvent
import com.example.amphsesviewer.ui.core.ViewModelBase
import com.example.amphsesviewer.ui.core.ViewState
import com.example.amphsesviewer.feature.loadimage.domain.LoadImageInteractor

sealed class LoadImageAction :
    ViewAction {
    object OpenGallery : LoadImageAction()
    object NavigateBack: LoadImageAction()
}

sealed class LoadImageEvent:
    ViewEvent {
    object LoadClicked : LoadImageEvent()
    data class ImageSelected(val bitmap: Bitmap?): LoadImageEvent()
    object SaveClicked: LoadImageEvent()
}

data class LoadImageState(
    var bitmap: Bitmap?
): ViewState

class LoadImageViewModel(
    private val interactor: LoadImageInteractor,
    initState: LoadImageState = LoadImageState(
        null
    )
) : ViewModelBase<LoadImageState, LoadImageAction, LoadImageEvent>(initState) {

    init {

    }

    private fun saveImage(bitmap: Bitmap?){
        compositeDisposable.add(interactor.saveBitmap(bitmap).subscribe {
            sendAction(LoadImageAction.NavigateBack)
        })
    }

    override fun invoke(event: LoadImageEvent) {
        when (event) {
            is LoadImageEvent.LoadClicked -> sendAction(
                LoadImageAction.OpenGallery
            )
            is LoadImageEvent.ImageSelected -> sendNewState {
                copy(
                    bitmap = event.bitmap
                )
            }
            is LoadImageEvent.SaveClicked -> saveImage(viewState.value?.bitmap)
        }
    }
}
