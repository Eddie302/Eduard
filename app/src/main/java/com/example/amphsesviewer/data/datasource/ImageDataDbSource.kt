package com.example.amphsesviewer.data.datasource

import com.example.amphsesviewer.data.datasource.interfaces.IImageDataDbSource
import com.example.amphsesviewer.data.db.DatabaseStorage
import com.example.amphsesviewer.data.db.model.ImageSM
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ImageDataDbSource @Inject constructor(
    private val databaseStorage: DatabaseStorage
): IImageDataDbSource {

    override fun loadImagesData(): Observable<List<ImageSM>> =
        RxJavaBridge.toV3Observable(databaseStorage.imageDao().getAll())

    override fun loadImagesData(idList: List<Long>): Single<List<ImageSM>> =
        RxJavaBridge.toV3Single(databaseStorage.imageDao().getImagesData(idList))

    override fun deleteImageData(imageId: Long) {
        databaseStorage.imageDao().delete(imageId)
    }

    override fun saveImageData(filename: String) {
        databaseStorage.imageDao().insert(ImageSM(fileName = filename))
    }

    override fun deleteImagesData(imageIds: List<Long>) {
        databaseStorage.imageDao().delete(imageIds)
    }
}