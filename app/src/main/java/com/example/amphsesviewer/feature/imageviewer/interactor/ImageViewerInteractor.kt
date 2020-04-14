package com.example.amphsesviewer.feature.imageviewer.interactor

import android.graphics.Bitmap
import com.example.amphsesviewer.domain.repository.IImageRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ImageViewerInteractor @Inject constructor(
    private val imageRepository: IImageRepository
) : IImageViewerInteractor {
    override fun loadImages(idList: List<Long>): Single<List<Bitmap>> =
        imageRepository.loadBitmaps(idList)
}