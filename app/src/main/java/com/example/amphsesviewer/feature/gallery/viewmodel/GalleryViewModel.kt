package com.example.amphsesviewer.feature.gallery.viewmodel

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.ImageData
import com.example.amphsesviewer.domain.model.ImageUI
import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import com.example.amphsesviewer.feature.gallery.interactor.IGalleryInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.collections.HashMap


sealed class GalleryEvent : ViewEvent {
    object LoadClicked : GalleryEvent()
    data class DeleteImage(val imageUI: ImageUI): GalleryEvent()
}

sealed class GalleryAction:
    ViewAction {
    object OpenImageLoader : GalleryAction()
    data class ShowError(val t: Throwable): GalleryAction()
}

enum class GalleryMode {
    View,
    Edit
}

data class GalleryState(
    val mode: GalleryMode = GalleryMode.View,
    val imagesDataMap: Map<Long, ImageData> = HashMap(),
    val imagesMap: Map<Long, Bitmap?> = HashMap()
): ViewState

class GalleryViewModel(
    private val interactor: IGalleryInteractor,
    initState: GalleryState = GalleryState()
) : ViewModelBase<GalleryState, GalleryAction, GalleryEvent>(initState) {

    init {
        val disposable = interactor.loadImagesData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                val newMap: MutableMap<Long, Bitmap?> = it.associateBy({ it.id }, { null }).toMutableMap()

                newMap.forEach { (id, _) ->
                    if (viewState.value!!.imagesMap[id] != null) {
                        newMap[id] = viewState.value!!.imagesMap[id]
                    }
                }

                sendNewState {
                    copy(
                        imagesDataMap = it.associateBy({it.id}, {it}).toMutableMap(),
                        imagesMap = newMap
                    )
                }
            }
            .flatMap {
                Observable.fromIterable(it)
            }
            .subscribe({ imageData ->
                if (viewState.value!!.imagesMap[imageData.id] == null) {
                    interactor.loadBitmapThumbnail(imageData.fileName)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { addBitmap(imageData.id, it) },
                            { sendAction(GalleryAction.ShowError(it)) }
                        )
                }
            }, {
                sendAction(GalleryAction.ShowError(it))
            })
        compositeDisposable.add(disposable)
    }

    private fun addBitmap (id: Long, bitmap: Bitmap?) {
        bitmap?.let {
            val map = viewState.value!!.imagesMap.toMutableMap()
            map[id] = bitmap
            sendNewState {
                copy(
                    imagesMap = map
                )
            }
        }
    }

    private fun deleteImage(imageData: ImageData?) {
        interactor.deleteImage(imageData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, { sendAction(GalleryAction.ShowError(it)) })
    }

    override fun invoke(event: GalleryEvent) {
        when (event) {
            is GalleryEvent.LoadClicked -> sendAction(GalleryAction.OpenImageLoader)
            is GalleryEvent.DeleteImage -> deleteImage(viewState.value!!.imagesDataMap[event.imageUI.id])
        }
    }
}