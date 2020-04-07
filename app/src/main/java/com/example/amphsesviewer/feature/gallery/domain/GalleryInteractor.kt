package com.example.amphsesviewer.feature.gallery.domain

import android.graphics.Bitmap
import com.example.amphsesviewer.data.repository.GalleryRepository
import com.example.amphsesviewer.domain.repository.IGalleryRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class GalleryInteractor @Inject constructor(
    private val galleryRepository: IGalleryRepository
) : IGalleryInteractor {

    override fun loadImages() : Single<List<Bitmap>> {
        return galleryRepository.loadImages()
    }

    override fun getImageReceiver() : Flowable<Bitmap> {
        return galleryRepository.bitmap()
    }

    override fun saveBitmap(bitmap: Bitmap): Completable {
        return galleryRepository.saveBitmap(bitmap)
    }
}