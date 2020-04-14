package com.example.amphsesviewer.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R
import com.example.amphsesviewer.domain.model.ImageUI
import com.example.amphsesviewer.ui.viewholders.ImageThumbnailViewHolder
import kotlin.collections.ArrayList

class GalleryAdapter(private val context: Context?): RecyclerView.Adapter<ImageThumbnailViewHolder>() {
    lateinit var itemLongClickCallback: (imageData: ImageUI) -> Unit
    lateinit var itemClickCallback: (selectedItemPosition: Int, idList: List<Long>) -> Unit
    var images: List<ImageUI> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageThumbnailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_grid_item_layout, parent, false)
        return ImageThumbnailViewHolder(view)
    }

    override fun onViewAttachedToWindow(holder: ImageThumbnailViewHolder) {
        holder.itemView.run {
            setOnLongClickListener {
                val position = holder.adapterPosition
                itemLongClickCallback(images[position])
                false
            }
            setOnClickListener {
                val position = holder.adapterPosition
                itemClickCallback(position, images.map { it.id })
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: ImageThumbnailViewHolder) {
        holder.itemView.run {
            setOnLongClickListener(null)
            setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageThumbnailViewHolder, position: Int) {
        holder.bind(images[position])
    }
}