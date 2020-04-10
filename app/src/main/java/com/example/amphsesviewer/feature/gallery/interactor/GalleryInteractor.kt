package com.example.amphsesviewer.feature.gallery.interactor

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.model.ImageData
import com.example.amphsesviewer.domain.repository.IGalleryRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GalleryInteractor @Inject constructor(
    private val galleryRepository: IGalleryRepository
) : IGalleryInteractor {

    override fun loadImagesData() : Single<List<ImageData>> {
        return galleryRepository.loadImagesData()
    }

//    override fun getImageReceiver() : Flowable<Bitmap> {
//        return galleryRepository.bitmap()
//    }

    override fun saveBitmap(bitmap: Bitmap): Completable {
        return galleryRepository.saveBitmap(bitmap)
    }

    override fun loadBitmap(id: String): Single<Bitmap?> {
        return galleryRepository.loadBitmap(id)
    }
}