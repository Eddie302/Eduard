package com.example.amphsesviewer.data.db.dao

import androidx.room.*
import com.example.amphsesviewer.data.db.model.ImageSM
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.Observable


@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageSM): Long

    @Query("SELECT * FROM image")
    fun getAll(): Observable<List<ImageSM>>

    @Delete
    fun delete(image: ImageSM): Int
}