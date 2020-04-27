package com.example.amphsesviewer.domain.repository

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.Album
import com.example.amphsesviewer.domain.model.ImageData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


interface IImageRepository {
    val newImageProvider: Flowable<ImageData>
    fun saveBitmap(bitmap: Bitmap?) : Completable
    fun loadImagesData(): Observable<List<ImageData>>
    fun loadBitmapThumbnail(filename: String, minWidth: Int, minHeight: Int) : Single<Bitmap?>
    fun loadBitmaps(idList: List<Long>) : Single<List<Bitmap>>
    fun deleteImage(imageData: ImageData?): Completable
}