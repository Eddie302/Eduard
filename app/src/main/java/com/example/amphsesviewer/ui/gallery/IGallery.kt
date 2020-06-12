package com.example.amphsesviewer.ui.gallery

import io.reactivex.rxjava3.core.Completable

interface IGallery {

    interface EditItemClickHandler {
        fun setSelected(itemId: Long)
        fun setUnselected(itemId: Long)
    }

    fun loadImages(imageIds: List<Long>?)
    fun loadAllImages()
    fun deleteImages(imageIds: List<Long>)

    var mode: GalleryMode
    var checkedIds: HashSet<Long>

    var itemClickHandler: (selectedItemPosition: Int, idList: List<Long>) -> Unit
    var onImagesLoadedCallback: () -> Unit
}