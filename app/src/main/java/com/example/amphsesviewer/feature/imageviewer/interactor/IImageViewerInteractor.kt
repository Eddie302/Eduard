package com.example.amphsesviewer.feature.imageviewer.interactor

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Single

interface IImageViewerInteractor {

    fun loadImages(idList: List<Long>): Single<List<Bitmap>>
}