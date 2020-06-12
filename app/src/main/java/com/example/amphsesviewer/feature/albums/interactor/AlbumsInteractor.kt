package com.example.amphsesviewer.feature.albums.interactor

import com.example.amphsesviewer.domain.model.Album
import com.example.amphsesviewer.domain.repository.IAlbumsRepository
import com.example.amphsesviewer.domain.repository.IImageRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class AlbumsInteractor @Inject constructor(
    private val albumsRepository: IAlbumsRepository
) : IAlbumsInteractor {

    override fun createAlbum(name: String, imageIds: List<Long>) {
        TODO("Not yet implemented")
    }

    override fun createAlbum(name: String): Completable {
        return albumsRepository.createAlbum(name)
    }

    override fun loadAlbums(): Observable<List<Album>> {
        return albumsRepository.loadAlbums()
    }

    override fun saveAlbumChanges(albumId: Long, oldImageIds: List<Long>, newImageIds: List<Long>): Completable {
        return if (oldImageIds.size > newImageIds.size) {
            val diff = oldImageIds.subtract(newImageIds).toList()
            albumsRepository.removeImages(albumId, diff)
        } else {
            val diff = newImageIds.subtract(oldImageIds).toList()
            albumsRepository.addImages(albumId, diff)
        }
    }
}