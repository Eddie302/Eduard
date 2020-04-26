package com.example.amphsesviewer.data.db.model

import androidx.room.Entity

@Entity(primaryKeys = ["albumId", "imageId"])
class ImageAlbumCrossRef(
    val albumId: Long,
    val imageId: Long
)