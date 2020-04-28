package com.example.amphsesviewer.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.amphsesviewer.data.db.model.AlbumSM
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface AlbumDao {

    @Insert
    fun insert(albumSM: AlbumSM)

    @Query("SELECT * FROM album WHERE albumId = :id")
    fun getById(id: Long): Single<AlbumSM>

    @Query("SELECT * FROM album")
    fun getAll(): Observable<List<AlbumSM>>

    @Query("DELETE FROM album WHERE albumId = :id")
    fun delete(id: Long): Int

    @Query("DELETE FROM album WHERE albumId IN (:idList)")
    fun delete(idList: List<Long>) : Int
}