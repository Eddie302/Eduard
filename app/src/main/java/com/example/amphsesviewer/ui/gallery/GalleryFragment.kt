package com.example.amphsesviewer.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.amphsesviewer.R
import com.example.amphsesviewer.databinding.FragmentGalleryBinding
import com.example.amphsesviewer.domain.model.ImageUI
import com.example.amphsesviewer.feature.di.FeatureComponentManager
import com.example.amphsesviewer.feature.gallery.viewmodel.GalleryViewModelFactory
import com.example.amphsesviewer.feature.gallery.viewmodel.GalleryAction
import com.example.amphsesviewer.feature.gallery.viewmodel.GalleryEvent
import com.example.amphsesviewer.feature.gallery.viewmodel.GalleryState
import com.example.amphsesviewer.feature.gallery.viewmodel.GalleryViewModel
import com.example.amphsesviewer.ui.adapters.ImagesAdapter
import com.example.amphsesviewer.ui.diffutils.ImageDiffUtilCallback
import java.lang.ref.SoftReference
import javax.inject.Inject

class GalleryFragment : Fragment() {

    private val itemLongClickCallback = { imageData: ImageUI ->
        viewModel(GalleryEvent.DeleteImage(imageData))
    }

    private var binding: FragmentGalleryBinding? = null

    @Inject
    lateinit var galleryViewModelFactory: GalleryViewModelFactory

    private val viewModel: GalleryViewModel by viewModels { galleryViewModelFactory }
    private lateinit var imagesAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        FeatureComponentManager.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val layoutManager = GridLayoutManager(context, 3)
        imagesAdapter = ImagesAdapter(context)
        imagesAdapter.itemLongClickCallback = itemLongClickCallback
        viewModel.run {
            action.observe(viewLifecycleOwner, Observer {
                processAction(it)
            })
            viewState.observe(viewLifecycleOwner, Observer {
                renderState(it)
            })
        }
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding?.apply {
            this.btnLoad.setOnClickListener { viewModel(GalleryEvent.LoadClicked) }
            this.rvImages.layoutManager = layoutManager
            this.rvImages.adapter = imagesAdapter

//            val globalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
//                override fun onGlobalLayout() {
//                    (rvImages.layoutManager as GridLayoutManager).let {
//                        if (it.childCount > 0) {
//                            it.getChildAt(0)?.apply {
//
//                            }
//                            rvImages.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                        }
//                    }
//                }
//            }
//
//            this.rvImages.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        }?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun renderState(state: GalleryState) {
        imagesAdapter.run{
            val imageList: List<ImageUI> = state.imagesMap.toSortedMap().map {
                ImageUI(it.key, SoftReference(it.value))
            }
            val result = with(ImageDiffUtilCallback(images, imageList)) {
                DiffUtil.calculateDiff(this)
            }
            images = imageList
            result.dispatchUpdatesTo(this)
        }
    }

    private fun processAction(action: GalleryAction) = when (action) {
        is GalleryAction.OpenImageLoader -> findNavController().navigate(R.id.action_nav_gallery_to_loadImage)
        is GalleryAction.ShowError -> Toast.makeText(context, action.t.message, Toast.LENGTH_LONG).show()
    }
}
