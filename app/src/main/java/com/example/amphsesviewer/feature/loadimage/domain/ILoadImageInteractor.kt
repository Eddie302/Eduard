package com.example.amphsesviewer.feature.loadimage.domain

import android.graphics.Bitmap
import io.reactivex.Completable

interface ILoadImageInteractor {

    fun saveBitmap(bitmap: Bitmap?) : Completable
}