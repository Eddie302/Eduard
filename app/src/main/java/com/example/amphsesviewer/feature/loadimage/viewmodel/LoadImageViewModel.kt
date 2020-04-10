package com.example.amphsesviewer.feature.loadimage.viewmodel

import android.graphics.Bitmap
import com.example.amphsesviewer.feature.loadimage.interactor.ILoadImageInteractor
import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

sealed class LoadImageAction :
    ViewAction {
    object OpenGallery : LoadImageAction()
    object NavigateBack: LoadImageAction()
    data class ShowError(val t: Throwable): LoadImageAction()
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
    private val interactor: ILoadImageInteractor,
    initState: LoadImageState = LoadImageState(
        null
    )
) : ViewModelBase<LoadImageState, LoadImageAction, LoadImageEvent>(initState) {

    init {

    }

    private fun saveImage(bitmap: Bitmap?){
        compositeDisposable.add(interactor.saveBitmap(bitmap).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            sendAction(LoadImageAction.NavigateBack)
        }, {
            sendAction(LoadImageAction.ShowError(it))
        })
        )
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
