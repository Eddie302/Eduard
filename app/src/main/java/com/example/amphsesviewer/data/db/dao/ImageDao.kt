package com.example.amphsesviewer.data.db.dao

import androidx.room.*
import com.example.amphsesviewer.data.db.model.ImageSM
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageSM): Long

    @Query("SELECT * FROM image")
    fun getAll(): Observable<List<ImageSM>>

    @Query("SELECT * FROM image WHERE imageId IN (:idList)")
    fun getImagesData(idList: List<Long>): Single<List<ImageSM>>

    @Query("DELETE FROM image WHERE imageId = :id")
    fun delete(id: Long): Int
}