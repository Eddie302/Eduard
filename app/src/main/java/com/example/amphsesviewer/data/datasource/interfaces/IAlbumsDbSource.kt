package com.example.amphsesviewer.data.datasource.interfaces

import com.example.amphsesviewer.domain.model.Album
import io.reactivex.rxjava3.core.Observable

interface IAlbumsDbSource {
    fun loadAlbums(): Observable<List<Album>>
    fun createAlbum(name: String)
}