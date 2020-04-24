package com.example.amphsesviewer.ui.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView
import java.lang.ref.SoftReference

class ImageContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0)
    : AppCompatImageView(context, attrs, defStyleAttr) {

    private var bitmapRef: SoftReference<Bitmap>? = null
//    private val scaleGestureListener = object: ScaleGestureDetector.SimpleOnScaleGestureListener() {
//
//        override fun onScale(detector: ScaleGestureDetector?): Boolean {
//            detector?.let {
//                val scaleFactor = it.scaleFactor
//                scaleBitmap(scaleFactor)
//            }
//            return true
//        }
//    }
//    private val scaleGestureDetector = ScaleGestureDetector(context, scaleGestureListener)

    fun setBitmap(bitmapRef: SoftReference<Bitmap>) {
        bitmapRef.get()?.let {
            this.bitmapRef = bitmapRef
        }
    }

    fun setupBitmap(viewWidth: Int, viewHeight: Int) {
        this.bitmapRef?.get()?.let {
            val scale = if (it.width > it.width) {
                viewWidth / it.width.toFloat()
            } else {
                viewHeight / it.height.toFloat()
            }
            val newWidth = it.width * scale
            val newHeight = it.height * scale
            val newBitmap = Bitmap.createScaledBitmap(it, newWidth.toInt(), newHeight.toInt(), false)
            setImageBitmap(newBitmap)
        }
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        scaleGestureDetector.onTouchEvent(event)
//        return true
//    }
//
//    fun scaleBitmap(scaleFactor: Float) {
//        this.bitmapRef?.get()?.let {
//
//            val scale = if (it.width > it.width) {
//                this.width / it.width.toFloat() * scaleFactor
//            } else {
//                this.height / it.height.toFloat() * scaleFactor
//            }
//
//            val newWidth = it.width * scale
//            val newHeight = it.height * scale
//
//            val matrix = Matrix()
//            matrix.setScale(scale, scale)
//            val newBitmap = Bitmap.createScaledBitmap(it, newWidth.toInt(), newHeight.toInt(), false)
//            this.scaleType = ScaleType.CENTER
//            this.setImageBitmap(newBitmap)
//        }
//    }
}