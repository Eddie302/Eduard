package com.example.amphsesviewer.ui.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.amphsesviewer.domain.model.Album

class AlbumsDiffUtilCallback(
    private val oldList: List<Album>,
    private val newList: List<Album>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }
}