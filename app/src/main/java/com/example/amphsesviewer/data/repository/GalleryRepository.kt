package com.example.amphsesviewer.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.example.amphsesviewer.domain.repository.IGalleryRepository
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalleryRepository @Inject constructor(
    val context: Context

) : IGalleryRepository {

    private val bitmapRelay: PublishRelay<Bitmap> = PublishRelay.create()

    override fun bitmap(): Flowable<Bitmap> {
        return bitmapRelay.toFlowable(BackpressureStrategy.LATEST)
    }

    override fun loadImages(): Single<List<Bitmap>> {
        TODO("Not yet implemented")
    }

    override fun saveBitmap(bitmap: Bitmap?) : Completable {

        return Completable.fromAction {
            if (bitmap != null) {
                bitmapRelay.accept(bitmap)

//            val file = File(dir, name)
//            val out = FileOutputStream(file)
//
//            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY_MAX, out)
//
//            out.flush()
//            out.close()
            }
        }
    }

    fun loadBitmaps() {

    }
}

private const val COMPRESS_QUALITY_MAX = 100