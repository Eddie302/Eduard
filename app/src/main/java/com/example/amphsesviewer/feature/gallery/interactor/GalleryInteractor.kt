package com.example.amphsesviewer.feature.gallery.interactor

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.ImageData
import com.example.amphsesviewer.domain.repository.IGalleryRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GalleryInteractor @Inject constructor(
    private val galleryRepository: IGalleryRepository
) : IGalleryInteractor {

    override val newImageProvider : Flowable<ImageData> = galleryRepository.newImageProvider

    override fun loadImagesData() : Observable<List<ImageData>> {
        return galleryRepository.loadImagesData()
    }

    override fun saveBitmap(bitmap: Bitmap): Completable {
        return galleryRepository.saveBitmap(bitmap)
    }

    override fun loadBitmapThumbnail(filename: String): Single<Bitmap?> {
        return galleryRepository.loadBitmapThumbnail(filename)
    }

    override fun loadBitmap(filename: String): Single<Bitmap?> {
        return galleryRepository.loadBitmap(filename)
    }

    override fun deleteImage(imageData: ImageData): Completable {
        return galleryRepository.deleteImage(imageData)
    }
}