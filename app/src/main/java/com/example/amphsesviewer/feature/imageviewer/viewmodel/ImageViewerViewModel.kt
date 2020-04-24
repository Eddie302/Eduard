package com.example.amphsesviewer.feature.imageviewer.viewmodel

import android.graphics.Bitmap
import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import com.example.amphsesviewer.feature.imageviewer.interactor.IImageViewerInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


sealed class ImageViewerEvent : ViewEvent {
    data class ViewLoaded(val idList: List<Long>): ImageViewerEvent()
}

sealed class ImageViewerAction : ViewAction {
    object ShowLoading: ImageViewerAction()
    object HideLoading: ImageViewerAction()
    data class ShowError(val t: Throwable): ImageViewerAction()
}

data class ImageViewerState(
    val images: List<Bitmap>? = null
) : ViewState

class ImageViewerViewModel(
    private val interactor: IImageViewerInteractor,
    initState: ImageViewerState = ImageViewerState()
) : ViewModelBase<ImageViewerState, ImageViewerAction, ImageViewerEvent>(initState) {

    private fun loadImages(idList: List<Long>) {
        interactor.loadImages(idList)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    sendAction(ImageViewerAction.HideLoading)
                    sendNewState {
                        copy(
                            images = it
                        )
                    }
                },
                onError = {
                    ImageViewerAction.ShowError(it)
                }
            )
    }

    override fun invoke(event: ImageViewerEvent) {
        when (event) {
            is ImageViewerEvent.ViewLoaded -> {
                sendAction(ImageViewerAction.ShowLoading)
                loadImages(event.idList)
            }
        }
    }
}