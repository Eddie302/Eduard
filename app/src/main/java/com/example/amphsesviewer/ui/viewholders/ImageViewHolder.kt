package com.example.amphsesviewer.ui.viewholders

import android.graphics.Bitmap
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R

class ImageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(bitmap: Bitmap) {
        view.findViewById<AppCompatImageView>(R.id.image).setImageBitmap(bitmap)
    }
}