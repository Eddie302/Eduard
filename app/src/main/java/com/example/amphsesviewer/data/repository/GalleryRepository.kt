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
import io.reactivex.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.kotlin.Observables
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class GalleryRepository @Inject constructor(
    private val databaseStorage: DatabaseStorage,
    context: Context
) : IGalleryRepository {
    private val dir = context.filesDir
    private val bitmapRelay: PublishRelay<ImageData> = PublishRelay.create()

    override val newImageProvider: Flowable<ImageData> = RxJavaBridge.toV3Flowable(bitmapRelay.toFlowable(BackpressureStrategy.LATEST))

    override fun loadImagesData(): Observable<List<ImageData>> {
        return RxJavaBridge.toV3Observable(databaseStorage.imageDao().getAll())
            .flatMap {
                Observable.fromIterable(it).map {
                    ImageData(it.id)
                }.toList().toObservable()
//                Flowable.fromIterable(imagesSM).map { imageSM ->
//                    ImageData(imageSM.id)
//                }
            }/*.onErrorReturnItem(emptyList())*/
    }

    override fun loadBitmap(id: Long) : Single<Bitmap?> {
        return Single.fromCallable {
            val file = File(dir, id.toString())
            BitmapFactory.decodeFile(file.absolutePath)
        }
    }

    override fun saveBitmap(bitmap: Bitmap?) : Completable {
        return Completable.fromCallable {
            if (bitmap != null) {
                val id = databaseStorage.imageDao().insert(ImageSM())
                val file = File(dir, id.toString())
                FileOutputStream(file).run {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY_MAX, this)
                    flush()
                    close()
                }
            }
        }
    }

    override fun deleteImage(id: Long): Completable {
        return Completable.fromCallable {
            val file = File(dir, id.toString())
            val success = file.delete()
            if (success) {
                databaseStorage.imageDao().delete(ImageSM(id))
            }
        }
    }
}

private const val COMPRESS_QUALITY_MAX = 100