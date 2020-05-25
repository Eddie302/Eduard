package com.example.amphsesviewer.data.repository

import android.graphics.Bitmap
import com.example.amphsesviewer.data.datasource.interfaces.IImageDataDbSource
import com.example.amphsesviewer.data.datasource.interfaces.IInternalStorageDataSource
import com.example.amphsesviewer.domain.model.ImageData
import com.example.amphsesviewer.domain.repository.IImageRepository
import com.jakewharton.rxrelay2.PublishRelay
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.UUID
import javax.inject.Inject


class ImageRepository @Inject constructor(
    private val imageDataDbSource: IImageDataDbSource,
    private val internalStorageDataSource: IInternalStorageDataSource
) : IImageRepository {
    private val bitmapRelay: PublishRelay<ImageData> = PublishRelay.create()

    override val newImageProvider: Flowable<ImageData> = RxJavaBridge.toV3Flowable(bitmapRelay.toFlowable(BackpressureStrategy.LATEST))

    override fun loadImagesData(): Observable<List<ImageData>> {
        return imageDataDbSource.loadImagesData()
            .flatMap {
                Observable.fromIterable(it).map { imageSM ->
                    ImageData(imageSM.imageId, imageSM.fileName)
                }.toList().toObservable()
            }
    }

    override fun loadImagesData(imageIds: List<Long>): Single<List<ImageData>> {
        return imageDataDbSource.loadImagesData(imageIds)
            .flatMap {
                Observable.fromIterable(it).map { imageSM ->
                    ImageData(imageSM.imageId, imageSM.fileName)
                }.toList()
            }
    }

    override fun loadBitmapThumbnail(filename: String, minWidth: Int, minHeight: Int) : Single<Bitmap?> {
        return Single.fromCallable {
            internalStorageDataSource.loadBitmapThumbnail(filename, minWidth, minHeight)
        }
    }

    override fun loadBitmaps(idList: List<Long>): Single<List<Bitmap>> {
        return imageDataDbSource.loadImagesData(idList)
            .flatMapObservable { Observable.fromIterable(it) }
            .map { internalStorageDataSource.loadBitmap(it.fileName) }
            .toList()
    }

    override fun saveBitmap(bitmap: Bitmap?) : Completable {
        return Completable.fromCallable {
            val filename = UUID.randomUUID().toString()
            internalStorageDataSource.saveBitmap(bitmap, filename)
            imageDataDbSource.saveImageData(filename)
        }
    }

    override fun deleteImage(imageData: ImageData?): Completable {
        return Completable.fromCallable {
            if (imageData != null) {
                val success = internalStorageDataSource.deleteBitmap(imageData.fileName)
                if (success) {
                    imageDataDbSource.deleteImageData(imageData.id)
                }
            }
        }
    }

    override fun deleteImages(imageIds: List<Long>): Completable {
        return Completable.fromCallable {
            imageDataDbSource.loadImagesData(imageIds)
                .subscribeBy (
                    onSuccess = {
                        it.forEach {
                            internalStorageDataSource.deleteBitmap(it.fileName)
                        }
                        imageDataDbSource.deleteImagesData(it.map { it.imageId })
                    },
                    onError = {}
                )
        }
    }
}