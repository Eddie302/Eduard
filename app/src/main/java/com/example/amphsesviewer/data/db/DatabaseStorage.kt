package com.example.amphsesviewer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.amphsesviewer.data.db.dao.ImageDao
import com.example.amphsesviewer.data.db.model.ImageSM

@Database(entities = [ImageSM::class], version = 3, exportSchema = false)
abstract class DatabaseStorage : RoomDatabase() {

    abstract fun imageDao() : ImageDao
}