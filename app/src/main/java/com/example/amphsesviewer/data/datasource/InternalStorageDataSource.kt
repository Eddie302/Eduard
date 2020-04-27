package com.example.amphsesviewer.data.datasource

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.amphsesviewer.data.datasource.interfaces.IInternalStorageDataSource
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class InternalStorageDataSource @Inject constructor(
    context: Context
): IInternalStorageDataSource {
    private val dir = context.filesDir

    override fun saveBitmap(bitmap: Bitmap?, filename: String) {
        if (bitmap != null) {
            val file = File(dir, filename)
            FileOutputStream(file).run {
                bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY_MAX, this)
                flush()
                close()
            }
        }
    }

    override fun deleteBitmap(filename: String): Boolean {
        val file = File(dir, filename)
        return file.delete()
    }

    override fun loadBitmap(filename: String): Bitmap {
        val file = File(dir, filename)
        return BitmapFactory.decodeFile(file.absolutePath)
    }

    override fun loadBitmapThumbnail(filename: String, minWidth: Int, minHeight: Int): Bitmap {
        val file = File(dir, filename)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, options)
        options.inSampleSize = calcInSampleSize(options, minWidth, minHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(file.absolutePath, options)
    }

    private fun calcInSampleSize(options: BitmapFactory.Options, minWidth: Int, minHeight: Int): Int {
        val width = options.outWidth
        val height = options.outHeight
        var inSampleSize = 1

        if (width > minWidth || height > minHeight) {
            val halfWidth = width / 2
            val halfHeight = height / 2

            while (halfWidth / inSampleSize > minWidth || halfHeight / inSampleSize > minHeight) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}

private const val COMPRESS_QUALITY_MAX = 100