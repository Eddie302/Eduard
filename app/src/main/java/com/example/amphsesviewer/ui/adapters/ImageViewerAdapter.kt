package com.example.amphsesviewer.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R
import com.example.amphsesviewer.ui.viewholders.ImageViewHolder
import java.lang.ref.SoftReference

class ImageViewerAdapter(private val context: Context?) : RecyclerView.Adapter<ImageViewHolder>() {
    var images: List<SoftReference<Bitmap>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_list_item_layout, parent, false)
        return ImageViewHolder(view, context!!)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }
}