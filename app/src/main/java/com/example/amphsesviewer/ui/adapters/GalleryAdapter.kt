package com.example.amphsesviewer.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amphsesviewer.R
import com.example.amphsesviewer.domain.model.ImageUI
import com.example.amphsesviewer.ui.gallery.IGallery
import com.example.amphsesviewer.ui.viewholders.ImageThumbnailViewHolder
import kotlin.collections.ArrayList

class GalleryAdapter(private val context: Context?): RecyclerView.Adapter<ImageThumbnailViewHolder>() {
//    lateinit var itemLongClickCallback: (imageData: ImageUI) -> Unit
    lateinit var itemLongClickCallback: () -> Unit
    lateinit var itemClickCallback: (selectedItemPosition: Int, idList: List<Long>) -> Unit
    lateinit var itemSizeChangedCallback: () -> Unit
    lateinit var editItemClickHandler: IGallery.EditItemClickHandler

    var images: List<ImageUI> = ArrayList()
    val checkedIds: HashSet<Long> = HashSet()

    var itemWidth = 0
        private set

    var itemHeight = 0
        private set

    var isEditEnabled: Boolean = false
        set(value) {
            if (value != field) {
                field = value
                notifyDataSetChanged()
            }
        }


    fun setItemSize(width: Int, height: Int) {
        itemWidth = width
        itemHeight = height
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageThumbnailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_grid_item_layout, parent, false)
        return ImageThumbnailViewHolder(view)
    }

    override fun onViewAttachedToWindow(holder: ImageThumbnailViewHolder) {
        holder.itemView.run {
            setOnLongClickListener {
                val position = holder.adapterPosition
                val image = images[position]
                itemLongClickCallback()
                checkedIds.add(image.id)
                image.isChecked = true
                false
            }

            setOnClickListener {
                if (isEditEnabled) {
                    val position = holder.adapterPosition
                    val image = images[position]

                    if (!image.isChecked) {
                        editItemClickHandler.setSelected(image.id)
//                        checkedIds.add(image.id)
                        image.isChecked = true
                    } else {
                        editItemClickHandler.setUnselected(image.id)
//                        checkedIds.remove(image.id)
                        image.isChecked = false
                    }

                    notifyItemChanged(position)
                } else {
                    val position = holder.adapterPosition
                    itemClickCallback(position, images.map { it.id })
                }
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
        holder.bind(images[position], isEditEnabled)

//        with(holder.itemView) {
//            if (width != 0 && height != 0) {
//                if (itemWidth != width || itemHeight != height) {
//                    itemWidth = width
//                    itemHeight = height
//                    itemSizeChangedCallback()
//                }
//            }
//        }
    }
}