package com.example.amphsesviewer.feature.gallery.domain

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.ImageData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single


interface IGalleryInteractor {
    fun loadImagesData() : Single<List<ImageData>>
//    fun getImageReceiver() : Flowable<Bitmap>
    fun saveBitmap(bitmap: Bitmap): Completable
    fun loadBitmap(id: String): Single<Bitmap?>
}