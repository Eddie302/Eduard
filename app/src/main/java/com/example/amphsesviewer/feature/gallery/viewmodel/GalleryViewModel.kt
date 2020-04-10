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
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


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

    private var imagesMap: MutableMap<Long, Bitmap?> = HashMap()

    init {
        interactor.loadImagesData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                imagesMap = it.associateBy( { it.id }, {it.bitmap} ).toMutableMap()
                sendNewState {
                    copy(
                        images = imagesMap.toSortedMap().map { ImageData(it.key, it.value) }
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
                        { addBitmap(imageData.id, it) },
                        { sendAction(GalleryAction.ShowError(it)) }
                    )
            }, {
                sendAction(GalleryAction.ShowError(it))
            })

        interactor.newImageProvider
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { addBitmap(it.id, it.bitmap) },
                { sendAction(GalleryAction.ShowError(it)) }
            )
    }

    private fun addBitmap (id: Long, bitmap: Bitmap?) {
        bitmap?.let {
            imagesMap[id] = it
            sendNewState {
                copy(
                    images = imagesMap.toSortedMap().map { ImageData(it.key, it.value) }
                )
            }
        }
    }

    override fun invoke(event: GalleryEvent) {
        when (event) {
            is GalleryEvent.LoadClicked -> sendAction(GalleryAction.OpenImageLoader)
        }
    }
}