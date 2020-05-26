package com.example.amphsesviewer.ui.gallery

interface IGallery {

    interface EditItemClickHandler {
        fun setSelected(itemId: Long)
        fun setUnselected(itemId: Long)
    }

    fun loadImages(ids: List<Long>?)
    fun loadAllImages()
    var mode: GalleryMode
    val checkedIds: HashSet<Long>

    var itemClickHandler: (selectedItemPosition: Int, idList: List<Long>) -> Unit
}