package com.example.amphsesviewer.ui.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.amphsesviewer.ui.gallery.ImageUI

class ImageDiffUtilCallback(
    private val oldList: List<ImageUI>,
    private val newList: List<ImageUI>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].image.get() === newList[newItemPosition].image.get()
}