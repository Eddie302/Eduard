package com.example.amphsesviewer.feature.loadimage.interactor

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.repository.IGalleryRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class LoadImageInteractor @Inject constructor(
    private val galleryRepository: IGalleryRepository
) : ILoadImageInteractor {

    override fun saveBitmap(bitmap: Bitmap?) : Completable {
        return galleryRepository.saveBitmap(bitmap)
    }
}