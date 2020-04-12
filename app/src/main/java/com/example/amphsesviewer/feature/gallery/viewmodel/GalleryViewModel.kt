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


sealed class GalleryEvent : ViewEvent {
    object LoadClicked : GalleryEvent()
    data class DeleteImage(val imageData: ImageData): GalleryEvent()
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
    val images: List<ImageData> = ArrayList()
): ViewState

class GalleryViewModel(
    private val interactor: IGalleryInteractor,
    initState: GalleryState = GalleryState()
) : ViewModelBase<GalleryState, GalleryAction, GalleryEvent>(initState) {

    private var imagesMap: MutableMap<Long, Pair<String, Bitmap?>> = HashMap()

    init {
        interactor.loadImagesData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                val newMap: MutableMap<Long, Pair<String, Bitmap?>> = it.associateBy({ it.id }, { Pair(it.fileName, it.bitmap) }).toMutableMap()

                //method 1
                newMap.forEach { (id, _) ->
                    if (imagesMap[id]?.second != null) {
                        newMap[id] = Pair(imagesMap[id]!!.first, imagesMap[id]!!.second)
                    }
                }

//                //method 2
//                imagesMap.mapValues {
//                    if (it.value != null) {
//                        if (newMap.containsKey(it.key)) {
//                            newMap[it.key] = it.value
//                        }
//                    }
//                }

                imagesMap = newMap

                sendNewState {
                    copy(
                        images = imagesMap.toSortedMap().map {
                            ImageData(it.key, it.value.first, it.value.second)
                        }
                    )
                }
            }
            .flatMap {
                Observable.fromIterable(it)
            }
            .subscribe({ imageData ->
                if (imagesMap[imageData.id]?.second == null) {
                    interactor.loadBitmap(imageData.fileName)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { addBitmap(imageData.id, imageData.fileName, it) },
                            { sendAction(GalleryAction.ShowError(it)) }
                        )
                }
            }, {
                sendAction(GalleryAction.ShowError(it))
            })
    }

    private fun addBitmap (id: Long, filename: String, bitmap: Bitmap?) {
        bitmap?.let {
            imagesMap[id] = Pair(filename, it)
            sendNewState {
                copy(
                    images = imagesMap.toSortedMap().map { ImageData(it.key, it.value.first, it.value.second) }
                )
            }
        }
    }

    private fun deleteImage(imageData: ImageData) {
        interactor.deleteImage(imageData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, { sendAction(GalleryAction.ShowError(it)) })
    }

    override fun invoke(event: GalleryEvent) {
        when (event) {
            is GalleryEvent.LoadClicked -> sendAction(GalleryAction.OpenImageLoader)
            is GalleryEvent.DeleteImage -> deleteImage(event.imageData)
        }
    }
}