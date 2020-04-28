package com.example.amphsesviewer.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R
import com.example.amphsesviewer.domain.model.Album
import com.example.amphsesviewer.ui.viewholders.AlbumViewHolder

class AlbumsAdapter(private val context: Context?) : RecyclerView.Adapter<AlbumViewHolder>() {

    var albums: List<Album> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_list_item_layout, parent, false)
        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albums[position])
    }
}