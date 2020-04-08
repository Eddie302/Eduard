package com.example.amphsesviewer.domain.repository

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.ImageData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single


interface IGalleryRepository {
//    fun bitmap() : Flowable<Bitmap>
    fun saveBitmap(bitmap: Bitmap?) : Completable
    fun loadImagesData(): Single<List<ImageData>>
    fun loadBitmap(id: String) : Single<Bitmap?>
}