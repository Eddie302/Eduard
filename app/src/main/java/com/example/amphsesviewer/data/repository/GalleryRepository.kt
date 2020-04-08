package com.example.amphsesviewer.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.amphsesviewer.data.db.DatabaseStorage
import com.example.amphsesviewer.data.db.model.ImageSM
import com.example.amphsesviewer.domain.model.ImageData
import com.example.amphsesviewer.domain.repository.IGalleryRepository
import com.jakewharton.rxrelay2.PublishRelay
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

import javax.inject.Inject


class GalleryRepository @Inject constructor(
    private val databaseStorage: DatabaseStorage,
    context: Context
) : IGalleryRepository {

    private val dir = context.filesDir

    private val bitmapRelay: PublishRelay<Bitmap> = PublishRelay.create()

//    override fun bitmap(): Flowable<Bitmap> {
//        return bitmapRelay.toFlowable(BackpressureStrategy.LATEST)
//    }

    override fun loadImagesData(): Single<List<ImageData>> {
        return RxJavaBridge.toV3Single(databaseStorage.imageDao().getAll())
            .flatMapObservable { imagesSM ->
                Observable.fromIterable(imagesSM).map { imageSM ->
                    ImageData(imageSM.id.toString())
                }
            }.toList()/*.onErrorReturnItem(emptyList())*/
    }

    override fun loadBitmap(id: String) : Single<Bitmap?> {
        return Single.fromCallable {
            val file = File(dir, id)
            BitmapFactory.decodeFile(file.absolutePath)
        }
    }

    override fun saveBitmap(bitmap: Bitmap?) : Completable {

        return Completable.fromCallable {
            if (bitmap != null) {
                val fileName = databaseStorage.imageDao().insert(ImageSM()).toString()
//                bitmapRelay.accept(bitmap)
                val file = File(dir, fileName)
                FileOutputStream(file).run {
                    val success = bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY_MAX, this)
                    flush()
                    close()
                }
            }
        }
    }
}

private const val COMPRESS_QUALITY_MAX = 100