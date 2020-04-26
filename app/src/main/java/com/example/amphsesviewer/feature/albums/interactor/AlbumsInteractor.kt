package com.example.amphsesviewer.feature.albums.interactor

import com.example.amphsesviewer.domain.repository.IImageRepository
import javax.inject.Inject

class AlbumsInteractor @Inject constructor(
    private val repository: IImageRepository
) : IAlbumsInteractor {


}