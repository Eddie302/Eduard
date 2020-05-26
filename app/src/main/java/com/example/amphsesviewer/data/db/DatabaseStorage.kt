package com.example.amphsesviewer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.amphsesviewer.data.db.dao.ImageDao
import com.example.amphsesviewer.data.db.dao.AlbumDao
import com.example.amphsesviewer.data.db.dao.ImageAlbumDao
import com.example.amphsesviewer.data.db.model.AlbumSM
import com.example.amphsesviewer.data.db.model.ImageAlbumCrossRef
import com.example.amphsesviewer.data.db.model.ImageSM

@Database(entities = [ImageSM::class, AlbumSM::class, ImageAlbumCrossRef::class], version = 6, exportSchema = false)
abstract class DatabaseStorage : RoomDatabase() {
    abstract fun imageDao() : ImageDao
    abstract fun albumDao() : AlbumDao
    abstract fun imageAlbumDao() : ImageAlbumDao
}