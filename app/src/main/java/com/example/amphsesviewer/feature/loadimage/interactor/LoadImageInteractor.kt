package com.example.amphsesviewer.feature.loadimage.interactor

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.repository.IImageRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class LoadImageInteractor @Inject constructor(
    private val imageRepository: IImageRepository
) : ILoadImageInteractor {

    override fun saveBitmap(bitmap: Bitmap?) : Completable {
        return imageRepository.saveBitmap(bitmap)
    }
}