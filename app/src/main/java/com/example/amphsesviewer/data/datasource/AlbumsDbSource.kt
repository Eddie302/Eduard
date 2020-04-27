package com.example.amphsesviewer.data.datasource

import com.example.amphsesviewer.data.datasource.interfaces.IAlbumsDbSource
import com.example.amphsesviewer.data.db.DatabaseStorage
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
                    val imagesIdList = databaseStorage.imageAlbumDao().getAlbumWithImageIds(it.albumId).imageIds
                    Album(it.albumId, it.name, imagesIdList)
                }.toList().toObservable()
            }
    }
}