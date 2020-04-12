package com.example.amphsesviewer.ui.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R
import com.example.amphsesviewer.domain.model.ImageData

class ImageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(imageData: ImageData) {
        val pb = view.findViewById<ContentLoadingProgressBar>(R.id.pb)
        if (imageData.bitmap != null) {
            pb.hide()
        } else {
            pb.show()
        }
        view.findViewById<AppCompatImageView>(R.id.image).setImageBitmap(imageData.bitmap)
    }
}