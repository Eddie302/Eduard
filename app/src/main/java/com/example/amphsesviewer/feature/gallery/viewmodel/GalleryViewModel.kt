package com.example.amphsesviewer.feature.gallery.viewmodel

import android.graphics.Bitmap
import com.example.amphsesviewer.ui.core.ViewAction
import com.example.amphsesviewer.ui.core.ViewEvent
import com.example.amphsesviewer.ui.core.ViewModelBase
import com.example.amphsesviewer.ui.core.ViewState
import com.example.amphsesviewer.feature.gallery.domain.GalleryInteractor
import com.example.amphsesviewer.feature.gallery.domain.IGalleryInteractor
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

sealed class GalleryEvent :
    ViewEvent {
    object LoadClicked : GalleryEvent()
}

sealed class GalleryAction:
    ViewAction {
    object OpenImageLoader : GalleryAction()
}

data class GalleryState(
    val images: MutableList<Bitmap> = ArrayList()
): ViewState

class GalleryViewModel(
    private val interactor: IGalleryInteractor,
    initState: GalleryState = GalleryState()
) : ViewModelBase<GalleryState, GalleryAction, GalleryEvent>(initState) {



    init {
        interactor.loadImages()
        compositeDisposable.add(
            interactor.getImageReceiver()
                .subscribeOn(Schedulers.io())
                .subscribe {
                    viewState.value?.images?.add(it)
                    postNewState {
                        copy(
                            images = images
                        )
                    }
                }
        )

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