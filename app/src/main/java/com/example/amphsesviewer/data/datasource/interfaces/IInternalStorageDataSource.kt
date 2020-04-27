package com.example.amphsesviewer.data.datasource.interfaces

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Completable

interface IInternalStorageDataSource {
    fun saveBitmap(bitmap: Bitmap?, filename: String)
    fun loadBitmap(filename: String): Bitmap
    fun deleteBitmap(filename: String): Boolean
    fun loadBitmapThumbnail(filename: String, minWidth: Int, minHeight: Int): Bitmap
}