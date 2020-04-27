package com.example.amphsesviewer.feature.albums.interactor

import com.example.amphsesviewer.domain.model.Album
import com.example.amphsesviewer.domain.repository.IAlbumsRepository
import com.example.amphsesviewer.domain.repository.IImageRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class AlbumsInteractor @Inject constructor(
    private val albumsRepository: IAlbumsRepository
) : IAlbumsInteractor {

    override fun createAlbum(name: String, imageIds: List<Long>) {
        TODO("Not yet implemented")
    }

    override fun addIdsToAlbum(albumId: Long, imageIds: List<Long>) {
        TODO("Not yet implemented")
    }

    override fun loadAlbums(): Observable<List<Album>> {
        return albumsRepository.loadAlbums()
    }
}