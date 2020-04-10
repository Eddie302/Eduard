package com.example.amphsesviewer.feature.gallery.viewmodel

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.ImageData
import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import com.example.amphsesviewer.feature.gallery.interactor.IGalleryInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.ref.WeakReference


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
    val images: List<ImageData> = ArrayList()
): ViewState

class GalleryViewModel(
    private val interactor: IGalleryInteractor,
    initState: GalleryState = GalleryState()
) : ViewModelBase<GalleryState, GalleryAction, GalleryEvent>(initState) {

    private val imagesMap: MutableMap<String, Bitmap?> = HashMap()

    init {
        interactor.loadImagesData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                sendNewState {
                    copy(
                        images = it
                    )
                }
            }
            .flatMapObservable {
                Observable.fromIterable(it)
            }
            .subscribe({ imageData ->
                interactor.loadBitmap(imageData.id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { addBitmap(it, imageData.id) },
                        { sendAction(GalleryAction.ShowError(it)) }
                    )
            }, {
                sendAction(GalleryAction.ShowError(it))
            })
    }

    private fun addBitmap (bitmap: Bitmap?, id: String) {
        bitmap?.let {
            imagesMap[id] = it
            sendNewState {
                copy(
                    images = imagesMap.map { ImageData(it.key, it.value) }
                )
            }
        }
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