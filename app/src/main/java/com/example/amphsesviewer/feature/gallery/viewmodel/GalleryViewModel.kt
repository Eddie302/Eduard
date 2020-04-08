package com.example.amphsesviewer.feature.gallery.viewmodel

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.ImageData
import com.example.amphsesviewer.ui.core.ViewAction
import com.example.amphsesviewer.ui.core.ViewEvent
import com.example.amphsesviewer.ui.core.ViewModelBase
import com.example.amphsesviewer.ui.core.ViewState
import com.example.amphsesviewer.feature.gallery.domain.IGalleryInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers


sealed class GalleryEvent :
    ViewEvent {
    object LoadClicked : GalleryEvent()
}

sealed class GalleryAction:
    ViewAction {
    object OpenImageLoader : GalleryAction()
    data class ShowError(val t: Throwable): GalleryAction()
}

data class GalleryState(
    val images: MutableList<Bitmap> = ArrayList()
): ViewState

class GalleryViewModel(
    private val interactor: IGalleryInteractor,
    initState: GalleryState = GalleryState()
) : ViewModelBase<GalleryState, GalleryAction, GalleryEvent>(initState) {

    private fun addBitmap (bitmap: Bitmap?) {
        bitmap?.let {
            viewState.value?.images?.add(it)
            sendNewState {
                copy(
                    images = images
                )
            }
        }
    }

    init {
        interactor.loadImagesData()
            .subscribeOn(Schedulers.io())
            .flatMapObservable {
                Observable.fromIterable(it)
            }
            .subscribe({
                interactor.loadBitmap(it.id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            addBitmap(it)
                        }, {
                            sendAction(GalleryAction.ShowError(it))
                        }
                    )
            }, {
                sendAction(GalleryAction.ShowError(it))
            })

//        compositeDisposable.add(
//            interactor.getImageReceiver()
//                .subscribeOn(Schedulers.io())
//                .subscribe {
//                    viewState.value?.images?.add(it)
//                    postNewState {
//                        copy(
//                            images = images
//                        )
//                    }
//                }
//        )
    }

    private fun saveBitmap(bitmap: Bitmap, name: String) {

    }

    override fun invoke(event: GalleryEvent) {
        when (event) {
            is GalleryEvent.LoadClicked -> sendAction(
                GalleryAction.OpenImageLoader
            )
        }
    }
}