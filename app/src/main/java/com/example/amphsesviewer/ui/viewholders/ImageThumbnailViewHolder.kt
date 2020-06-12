package com.example.amphsesviewer.ui.viewholders

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R
import com.example.amphsesviewer.ui.gallery.ImageUI

class ImageThumbnailViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(imageUI: ImageUI, isEdit: Boolean) {

        //view/edit mode stuff
        if (isEdit) {
            view.findViewById<AppCompatImageView>(R.id.btn_check_image).run {
                visibility = View.VISIBLE
                if (imageUI.isChecked) {
                    drawable.setTint(Color.RED)
                } else {
                    drawable.setTint(Color.GRAY)
                }
            }
        } else {
            view.findViewById<AppCompatImageView>(R.id.btn_check_image).visibility = View.GONE
        }

        //progress bar stuff
        val pb = view.findViewById<ContentLoadingProgressBar>(R.id.pb)
        if (imageUI.image.get() != null) {
            pb.hide()
        } else {
            pb.show()
        }

        //bitmap stuff
        view.findViewById<AppCompatImageView>(R.id.image).run {
            setImageBitmap(imageUI.image.get())
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }
}