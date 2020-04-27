package com.example.amphsesviewer.data.datasource.interfaces

import com.example.amphsesviewer.data.db.model.ImageSM
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface IImageDataDbSource {
    fun loadImagesData(): Observable<List<ImageSM>>
    fun loadImagesData(idList: List<Long>): Single<List<ImageSM>>
    fun deleteImageData(imageId: Long)
    fun saveImageData(filename: String)
}