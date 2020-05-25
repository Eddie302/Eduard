package com.example.amphsesviewer.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class ImageSM(
    @PrimaryKey(autoGenerate = true)
    val imageId: Long = VAL_AUTO,
    val fileName: String
)

private const val VAL_AUTO = 0L