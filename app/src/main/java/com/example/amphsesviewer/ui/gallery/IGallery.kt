package com.example.amphsesviewer.ui.gallery

import io.reactivex.rxjava3.core.Completable

interface IGallery {

    interface EditItemClickHandler {
        fun setSelected(itemId: Long)
        fun setUnselected(itemId: Long)
    }

    fun loadImages(ids: List<Long>?)
    fun loadAllImages()
    var mode: GalleryMode
    var checkedIds: HashSet<Long>

    var itemClickHandler: (selectedItemPosition: Int, idList: List<Long>) -> Unit
    var onImagesLoadedCallback: () -> Unit
}