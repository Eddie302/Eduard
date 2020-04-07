package com.example.amphsesviewer.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class ImageSM(
    @PrimaryKey
    val id: Long = 0
)