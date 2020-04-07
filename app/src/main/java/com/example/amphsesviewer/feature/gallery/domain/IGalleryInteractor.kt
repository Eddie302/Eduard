package com.example.amphsesviewer.feature.gallery.domain

import android.graphics.Bitmap
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IGalleryInteractor {
    fun loadImages() : Single<List<Bitmap>>
    fun getImageReceiver() : Flowable<Bitmap>
    fun saveBitmap(bitmap: Bitmap): Completable
}