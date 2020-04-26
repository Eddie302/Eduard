package com.example.amphsesviewer.data.db.dao

import androidx.room.*
import com.example.amphsesviewer.data.db.model.AlbumWithImageIds

@Dao
interface ImageAlbumDao {
    @Transaction
    @Query("SELECT * FROM album WHERE albumId = :albumId")
    fun getAlbumWithImageIds(albumId: Long): AlbumWithImageIds

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insert(albumWithImageIds: AlbumWithImageIds)
}