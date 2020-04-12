package com.example.amphsesviewer.domain.model

import android.graphics.Bitmap

data class ImageData(
    val id: Long = 0,
    val fileName: String,
    val bitmap: Bitmap? = null
)