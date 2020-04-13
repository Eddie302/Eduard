package com.example.amphsesviewer.domain.model

import android.graphics.Bitmap
import java.lang.ref.SoftReference

data class ImageUI(
    val id: Long,
    val image: SoftReference<Bitmap?>
)