package com.example.amphsesviewer.ui.gallery

interface IGallery {

    fun loadImages(ids: List<Long>?)
    fun loadAllImages()
    var mode: GalleryMode
    val checkedIds: HashSet<Long>

    var itemClickHandler: (selectedItemPosition: Int, idList: List<Long>) -> Unit
}