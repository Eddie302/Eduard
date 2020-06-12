package com.example.amphsesviewer.data.db.dao

import androidx.room.*
import com.example.amphsesviewer.data.db.model.AlbumWithImageIds
import com.example.amphsesviewer.data.db.model.ImageAlbumCrossRef

@Dao
interface ImageAlbumDao {
    @Transaction
    @Query("SELECT * FROM album WHERE albumId = :albumId")
    fun getAlbumWithImageIds(albumId: Long): AlbumWithImageIds

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageAlbum: ImageAlbumCrossRef): Long

    @Delete
    fun delete(imageAlbum: ImageAlbumCrossRef)
}