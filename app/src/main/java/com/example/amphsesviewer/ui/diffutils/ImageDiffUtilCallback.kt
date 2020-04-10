package com.example.amphsesviewer.ui.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.amphsesviewer.domain.model.ImageData

class ImageDiffUtilCallback(
    private val oldList: List<ImageData>,
    private val newList: List<ImageData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].bitmap === newList[newItemPosition].bitmap
}