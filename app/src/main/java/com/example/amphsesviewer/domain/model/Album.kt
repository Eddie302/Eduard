package com.example.amphsesviewer.domain.model

import android.os.Parcelable

data class Album(
    val id: Long,
    val name: String,
    val ImagesId: List<Long>
)