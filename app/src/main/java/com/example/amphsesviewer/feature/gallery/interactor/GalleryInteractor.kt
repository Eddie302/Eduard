package com.example.amphsesviewer.feature.gallery.interactor

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.ImageData
import com.example.amphsesviewer.domain.repository.IImageRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GalleryInteractor @Inject constructor(
    private val imageRepository: IImageRepository
) : IGalleryInteractor {

    override val newImageProvider : Flowable<ImageData> = imageRepository.newImageProvider

    override fun loadImagesData() : Observable<List<ImageData>> {
        return imageRepository.loadImagesData()
    }

    override fun loadImagesData(imageIds: List<Long>) : Single<List<ImageData>> {
        return imageRepository.loadImagesData(imageIds)
    }

    override fun saveBitmap(bitmap: Bitmap): Completable {
        return imageRepository.saveBitmap(bitmap)
    }

    override fun loadBitmapThumbnail(filename: String, minWidth: Int, minHeight: Int): Single<Bitmap?> {
        return imageRepository.loadBitmapThumbnail(filename, minWidth, minHeight)
    }

    override fun deleteImage(imageData: ImageData?): Completable {
        return imageRepository.deleteImage(imageData)
    }

    override fun deleteImages(imageIds: List<Long>): Completable {
        return imageRepository.deleteImages(imageIds)
    }
}