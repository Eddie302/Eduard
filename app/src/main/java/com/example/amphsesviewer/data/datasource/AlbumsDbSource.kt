package com.example.amphsesviewer.data.datasource

import com.example.amphsesviewer.data.datasource.interfaces.IAlbumsDbSource
import com.example.amphsesviewer.data.db.DatabaseStorage
import com.example.amphsesviewer.data.db.model.AlbumSM
import com.example.amphsesviewer.data.db.model.ImageAlbumCrossRef
import com.example.amphsesviewer.domain.model.Album
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class AlbumsDbSource @Inject constructor(
    private val databaseStorage: DatabaseStorage
) : IAlbumsDbSource {


    override fun loadAlbums(): Observable<List<Album>> {
        return RxJavaBridge.toV3Observable(databaseStorage.albumDao().getAll())
            .flatMap {
                Observable.fromIterable(it).map {
                    val imagesId = databaseStorage.imageAlbumDao().getAlbumWithImageIds(it.albumId).images.map { it.imageId }
                    Album(it.albumId, it.name, imagesId)
                }.toList().toObservable()
            }
    }

    override fun createAlbum(name: String) {
        databaseStorage.albumDao().insert(AlbumSM(name = name))
    }

    override fun addImages(albumId: Long, imageIds: List<Long>) {
        imageIds.forEach {
            databaseStorage.imageAlbumDao().insert(ImageAlbumCrossRef(albumId, it))
        }
    }

    override fun removeImages(albumId: Long, imageIds: List<Long>) {
        imageIds.forEach {
            databaseStorage.imageAlbumDao().delete(ImageAlbumCrossRef(albumId, it))
        }
    }
}