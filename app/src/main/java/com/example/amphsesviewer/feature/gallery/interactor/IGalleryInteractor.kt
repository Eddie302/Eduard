package com.example.amphsesviewer.feature.gallery.interactor

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.ImageData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


interface IGalleryInteractor {
    val newImageProvider: Flowable<ImageData>
    fun loadImagesData() : Observable<List<ImageData>>
    fun saveBitmap(bitmap: Bitmap): Completable
    fun loadBitmapThumbnail(filename: String): Single<Bitmap?>
    fun loadBitmap(filename: String): Single<Bitmap?>
    fun deleteImage(imageData: ImageData?): Completable
}