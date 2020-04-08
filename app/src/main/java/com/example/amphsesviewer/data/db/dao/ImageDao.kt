package com.example.amphsesviewer.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.amphsesviewer.data.db.model.ImageSM
import io.reactivex.Single


@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageSM): Long

    @Query("SELECT * FROM image")
    fun getAll(): Single<List<ImageSM>>
}