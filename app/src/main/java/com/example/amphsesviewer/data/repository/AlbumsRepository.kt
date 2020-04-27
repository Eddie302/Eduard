package com.example.amphsesviewer.data.repository

import com.example.amphsesviewer.data.datasource.interfaces.IAlbumsDbSource
import com.example.amphsesviewer.domain.model.Album
import com.example.amphsesviewer.domain.repository.IAlbumsRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class AlbumsRepository @Inject constructor(
    private val albumsDbSource: IAlbumsDbSource
) : IAlbumsRepository {

    override fun loadAlbums(): Observable<List<Album>> {
        return albumsDbSource.loadAlbums()
    }

}