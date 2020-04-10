package com.example.amphsesviewer.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R
import com.example.amphsesviewer.domain.model.ImageData
import com.example.amphsesviewer.ui.viewholders.ImageViewHolder
import kotlin.collections.ArrayList

class ImagesAdapter(private val context: Context?): RecyclerView.Adapter<ImageViewHolder>() {

    var images: List<ImageData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_grid_item_layout, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }
}