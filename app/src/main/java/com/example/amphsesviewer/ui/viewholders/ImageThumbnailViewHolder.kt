package com.example.amphsesviewer.ui.viewholders

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R
import com.example.amphsesviewer.domain.model.ImageUI

class ImageThumbnailViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(imageUI: ImageUI) {
        val pb = view.findViewById<ContentLoadingProgressBar>(R.id.pb)
        if (imageUI.image.get() != null) {
            pb.hide()
        } else {
            pb.show()
        }
        view.findViewById<AppCompatImageView>(R.id.image).run {
            setImageBitmap(imageUI.image.get())
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }
}