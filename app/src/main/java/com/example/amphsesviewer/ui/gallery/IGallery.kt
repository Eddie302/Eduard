package com.example.amphsesviewer.ui.gallery

interface IGallery {

    fun loadImages(ids: List<Long>)
    fun loadAllImages()
    var mode: GalleryMode
}