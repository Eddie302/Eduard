package com.example.amphsesviewer.data.db.dao

import androidx.room.*
import com.example.amphsesviewer.data.db.model.ImageSM
import io.reactivex.Observable


@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageSM): Long

    @Query("SELECT * FROM image")
    fun getAll(): Observable<List<ImageSM>>

    @Query("DELETE FROM image WHERE id = :id")
    fun delete(id: Long): Int
}