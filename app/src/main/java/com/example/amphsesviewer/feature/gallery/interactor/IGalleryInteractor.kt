package com.example.amphsesviewer.feature.gallery.interactor

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.ImageData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single


interface IGalleryInteractor {
    fun loadImagesData() : Single<List<ImageData>>
    val newImageProvider: Flowable<ImageData>
    fun saveBitmap(bitmap: Bitmap): Completable
    fun loadBitmap(id: Long): Single<Bitmap?>
}