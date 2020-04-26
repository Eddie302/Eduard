package com.example.amphsesviewer.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class AlbumSM(
    @PrimaryKey(autoGenerate = true)
    val albumId: Long = 0,
    val name: String = ""
)