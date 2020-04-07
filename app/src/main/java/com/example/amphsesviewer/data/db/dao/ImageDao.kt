package com.example.amphsesviewer.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.amphsesviewer.data.db.model.ImageSM

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageSM)

    @Query("SELECT * FROM image")
    fun getAll(): List<ImageSM>
}