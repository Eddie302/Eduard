package com.example.amphsesviewer.feature.gallery.viewmodel

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.ImageData
import com.example.amphsesviewer.domain.model.ImageUI
import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import com.example.amphsesviewer.feature.gallery.interactor.IGalleryInteractor
import com.example.amphsesviewer.ui.gallery.GalleryMode
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.collections.HashMap


sealed class GalleryEvent : ViewEvent {
    object LoadClicked: GalleryEvent()
    data class DeleteClicked(val imageIds: List<Long>): GalleryEvent()
    data class DeleteImage(val imageUI: ImageUI): GalleryEvent()
    data class ItemSizeCalculated(val width: Int, val height: Int): GalleryEvent()
    data class ItemsAdded(val width: Int, val height: Int): GalleryEvent()
    object SetEditMode: GalleryEvent()
    object SetViewMode: GalleryEvent()
    data class LoadImages(val ids: List<Long>): GalleryEvent()
    object LoadAllImages: GalleryEvent()
}

sealed class GalleryAction:
    ViewAction {
    object OpenImageLoader : GalleryAction()
    data class ShowError(val t: Throwable): GalleryAction()
}

data class GalleryState(
    val mode: GalleryMode = GalleryMode.View,
    val imagesDataMap: Map<Long, ImageData> = emptyMap(),
    val imagesMap: Map<Long, Bitmap?> = emptyMap()
): ViewState

class GalleryViewModel(
    private val interactor: IGalleryInteractor,
    initState: GalleryState = GalleryState()
) : ViewModelBase<GalleryState, GalleryAction, GalleryEvent>(initState) {

    init {
//        val disposable = interactor.loadImagesData()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onNext = { processImageData(it) },
//                onError = { sendAction(GalleryAction.ShowError(it)) }
//            )
//        compositeDisposable.add(disposable)
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

    private fun processImageData(imageDataList: List<ImageData>) {
        val newMap: MutableMap<Long, Bitmap?> = imageDataList.associateBy({ it.id }, { null }).toMutableMap()

        //replace null stub with real bitmap if it's present on screen
        //so we load only those that aren't
        newMap.forEach { (id, _) ->
            if (viewState.value?.imagesMap?.get(id) != null) {
                newMap[id] = viewState.value?.imagesMap?.get(id)
            }
        }

        sendNewState {
            copy(
                imagesDataMap = imageDataList.associateBy({it.id}, {it}),
                imagesMap = newMap
            )
        }
    }

    private fun deleteImage(imageData: ImageData?) {
        interactor.deleteImage(imageData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { sendAction(GalleryAction.ShowError(it)) })
    }

    private fun deleteImages(imageIds: List<Long>) {
        interactor.deleteImages(imageIds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { sendNewState { copy(mode = GalleryMode.View) }},
                onError = { sendAction(GalleryAction.ShowError(it)) }
            )

    }

    private fun loadData() {
        val disposable = interactor.loadImagesData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { processImageData(it) },
                onError = { sendAction(GalleryAction.ShowError(it)) }
            )
        compositeDisposable.add(disposable)
    }

    private fun loadData(ids: List<Long>) {
        val disposable = interactor.loadImagesData(ids)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { processImageData(it) },
                onError = { sendAction(GalleryAction.ShowError(it)) }
            )
        compositeDisposable.add(disposable)
    }

    private fun loadBitmaps(width: Int, height: Int) {
        viewState.value?.let { state ->
            state.imagesDataMap.forEach { (_, imageData) ->
                //load bitmaps that aren't present on the screen
                //to fill the contents of an empty item
                if (state.imagesMap[imageData.id] == null) {
                    val disposable = interactor.loadBitmapThumbnail(imageData.fileName, width, height)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { addBitmap(imageData.id, it) },
                            { sendAction(GalleryAction.ShowError(it)) }
                        )
                    compositeDisposable.add(disposable)
                }
            }
        }
    }

    override fun invoke(event: GalleryEvent) {
        when (event) {
            is GalleryEvent.LoadClicked -> sendAction(GalleryAction.OpenImageLoader)
            is GalleryEvent.DeleteImage -> deleteImage(viewState.value!!.imagesDataMap[event.imageUI.id])
            is GalleryEvent.ItemSizeCalculated -> loadBitmaps(event.width, event.height)
            is GalleryEvent.ItemsAdded -> loadBitmaps(event.width, event.height)
            is GalleryEvent.SetEditMode -> sendNewState { copy(mode = GalleryMode.Edit) }
            is GalleryEvent.SetViewMode -> sendNewState { copy(mode = GalleryMode.View) }
            is GalleryEvent.DeleteClicked -> deleteImages(event.imageIds)
            is GalleryEvent.LoadImages -> loadData(event.ids)
            is GalleryEvent.LoadAllImages -> loadData()
        }
    }
}