package com.example.amphsesviewer.ui.viewholders

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R
import com.example.amphsesviewer.ui.views.ImageContainer
import java.lang.ref.SoftReference

class ImageViewHolder(private val view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
    fun bind(bitmapRef: SoftReference<Bitmap>) {
        view.findViewById<ImageContainer>(R.id.image).run {
            setBitmap(bitmapRef)
            doOnPreDraw {
                (it as ImageContainer).setupBitmap(it.width, it.height)
            }
        }
    }
}