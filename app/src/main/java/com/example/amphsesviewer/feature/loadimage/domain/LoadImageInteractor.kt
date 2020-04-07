package com.example.amphsesviewer.feature.loadimage.domain

import android.graphics.Bitmap
import com.example.amphsesviewer.data.repository.GalleryRepository
import com.example.amphsesviewer.domain.repository.IGalleryRepository
import io.reactivex.Completable
import java.io.File
import javax.inject.Inject

class LoadImageInteractor @Inject constructor(
    private val galleryRepository: IGalleryRepository
) : ILoadImageInteractor {

    override fun saveBitmap(bitmap: Bitmap?) : Completable {
        return galleryRepository.saveBitmap(bitmap)
    }
}