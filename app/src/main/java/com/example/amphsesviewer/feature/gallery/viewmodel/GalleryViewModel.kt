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
import java.lang.ref.WeakReference


sealed class GalleryEvent :
    ViewEvent {
    object LoadClicked : GalleryEvent()
}

sealed class GalleryAction:
    ViewAction {
    object OpenImageLoader : GalleryAction()
    data class ImageAdded(val imageId: String, val imageRef: WeakReference<Bitmap>): GalleryAction()
    data class ShowError(val t: Throwable): GalleryAction()
}

data class GalleryState(
    val images: List<Pair<String, WeakReference<Bitmap>>> = ArrayList()
): ViewState

class GalleryViewModel(
    private val interactor: IGalleryInteractor,
    initState: GalleryState = GalleryState()
) : ViewModelBase<GalleryState, GalleryAction, GalleryEvent>(initState) {

    private val imagesMap: MutableMap<String, Bitmap> = HashMap()

    private fun addBitmap (bitmap: Bitmap?, id: String) {
        bitmap?.let {
            imagesMap[id] = it
            sendAction(GalleryAction.ImageAdded(id, WeakReference(it)))

//            sendNewState {
//                copy(
//                    images = imagesMap.map {
//                        Pair(it.key, WeakReference(it.value))
//                    }
//                )
//            }
        }
    }

    init {
        interactor.loadImagesData()
            .subscribeOn(Schedulers.io())
            .flatMapObservable {
                Observable.fromIterable(it)
            }
            .subscribe({ imageData ->
                interactor.loadBitmap(imageData.id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { bitmap ->
                            addBitmap(bitmap, imageData.id)
                        }, { t ->
                            sendAction(GalleryAction.ShowError(t))
                        }
                    )
            }, {
                sendAction(GalleryAction.ShowError(it))
            })
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