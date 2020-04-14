package com.example.amphsesviewer.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.amphsesviewer.data.db.DatabaseStorage
import com.example.amphsesviewer.data.db.model.ImageSM
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
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject


class ImageRepository @Inject constructor(
    private val databaseStorage: DatabaseStorage,
    context: Context
) : IImageRepository {
    private val dir = context.filesDir
    private val desiredHeight = 150 * context.resources.displayMetrics.density
    private val bitmapRelay: PublishRelay<ImageData> = PublishRelay.create()

    override val newImageProvider: Flowable<ImageData> = RxJavaBridge.toV3Flowable(bitmapRelay.toFlowable(BackpressureStrategy.LATEST))

    override fun loadImagesData(): Observable<List<ImageData>> {
        return RxJavaBridge.toV3Observable(databaseStorage.imageDao().getAll())
            .flatMap {
                Observable.fromIterable(it).map {
                    ImageData(it.id, it.fileName)
                }.toList().toObservable()
            }
    }

    override fun loadBitmapThumbnail(filename: String) : Single<Bitmap?> {
        return Single.fromCallable {
            val file = File(dir, filename)
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(file.absolutePath, options)
            options.inSampleSize = calcInSampleSize(options)
            options.inJustDecodeBounds = false
            BitmapFactory.decodeFile(file.absolutePath, options)
        }
    }

    private fun loadBitmap(filename: String) : Bitmap {
        val file = File(dir, filename)
        return BitmapFactory.decodeFile(file.absolutePath)
    }

    override fun loadBitmaps(idList: List<Long>): Single<List<Bitmap>> {
        return RxJavaBridge.toV3Single(databaseStorage.imageDao().getImagesData(idList))
            .flatMapObservable { Observable.fromIterable(it) }
            .map { loadBitmap(it.fileName) }
            .toList()
    }

    override fun saveBitmap(bitmap: Bitmap?) : Completable {
        return Completable.fromCallable {
            if (bitmap != null) {
                val filename = UUID.randomUUID().toString()
                val file = File(dir, filename)
                FileOutputStream(file).run {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY_MAX, this)
                    flush()
                    close()
                }
                databaseStorage.imageDao().insert(ImageSM(fileName = filename))
            }
        }
    }

    override fun deleteImage(imageData: ImageData?): Completable {
        return Completable.fromCallable {
            if (imageData != null) {
                val file = File(dir, imageData.fileName)
                val success = file.delete()
                if (success) {
                    databaseStorage.imageDao().delete(imageData.id)
                }
            }
        }
    }

    private fun calcInSampleSize(options: BitmapFactory.Options): Int {
        val width = options.outWidth
        val height = options.outHeight

        var inSampleSize = 1

        if (height > desiredHeight) {
            val halfHeight = height / 2
            while (halfHeight / inSampleSize > desiredHeight) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}

private const val COMPRESS_QUALITY_MAX = 100