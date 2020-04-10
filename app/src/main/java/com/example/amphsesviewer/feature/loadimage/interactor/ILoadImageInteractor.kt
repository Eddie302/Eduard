package com.example.amphsesviewer.feature.loadimage.interactor

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Completable


interface ILoadImageInteractor {

    fun saveBitmap(bitmap: Bitmap?) : Completable
}