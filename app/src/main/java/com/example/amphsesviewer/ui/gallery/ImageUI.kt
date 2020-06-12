package com.example.amphsesviewer.ui.gallery

import android.graphics.Bitmap
import java.lang.ref.SoftReference

data class ImageUI(
    val id: Long,
    val image: SoftReference<Bitmap?>,
    var isChecked: Boolean = false
)