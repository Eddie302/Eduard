package com.example.amphsesviewer.domain.repository

import android.graphics.Bitmap
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IGalleryRepository {
    fun bitmap() : Flowable<Bitmap>
    fun saveBitmap(bitmap: Bitmap?) : Completable
    fun loadImages(): Single<List<Bitmap>>
}