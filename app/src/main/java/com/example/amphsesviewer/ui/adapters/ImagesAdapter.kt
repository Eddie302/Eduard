package com.example.amphsesviewer.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R
import com.example.amphsesviewer.databinding.ImageGridItemLayoutBinding
import java.lang.ref.WeakReference

class ImagesAdapter(private val context: Context?): RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    var images: MutableList<Pair<String, WeakReference<Bitmap>>> = ArrayList()
    private var binding: ImageGridItemLayoutBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = ImageGridItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_grid_item_layout, parent)
        return ImageViewHolder(binding!!.root)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            binding?.image?.setImageBitmap(images[position].second.get())
        }
    }
}