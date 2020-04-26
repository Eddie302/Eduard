package com.example.amphsesviewer.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R
import com.example.amphsesviewer.domain.model.Album
import com.google.android.material.textview.MaterialTextView

class AlbumViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(album: Album) {
        view.findViewById<MaterialTextView>(R.id.txt_album_name).run {
            text = album.name
        }
    }
}