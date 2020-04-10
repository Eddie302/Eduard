package com.example.amphsesviewer.data.db.dao

import androidx.room.*
import com.example.amphsesviewer.data.db.model.ImageSM
import io.reactivex.Single


@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageSM): Long

    @Query("SELECT * FROM image")
    fun getAll(): Single<List<ImageSM>>

    @Delete
    fun delete(image: ImageSM): Int
}