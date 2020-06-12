package com.example.amphsesviewer.feature.albums.interactor

import com.example.amphsesviewer.domain.model.Album
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface IAlbumsInteractor {
    fun createAlbum(name: String): Completable
    fun createAlbum(name: String, imageIds: List<Long>)
    fun saveAlbumChanges(albumId: Long, oldImageIds: List<Long>, newImageIds: List<Long>): Completable
    fun loadAlbums(): Observable<List<Album>>
}