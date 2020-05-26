package com.example.amphsesviewer.domain.repository

import com.example.amphsesviewer.domain.model.Album
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface IAlbumsRepository {
    fun loadAlbums(): Observable<List<Album>>
    fun createAlbum(name: String): Completable
    fun saveImages(albumId: Long, imageIds: List<Long>): Completable
}