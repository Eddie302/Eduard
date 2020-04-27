package com.example.amphsesviewer.feature.albums.interactor

import com.example.amphsesviewer.domain.model.Album
import io.reactivex.rxjava3.core.Observable

interface IAlbumsInteractor {
    fun createAlbum(name: String, imageIds: List<Long>)
    fun addIdsToAlbum(albumId: Long, imageIds: List<Long>)
    fun loadAlbums(): Observable<List<Album>>
}