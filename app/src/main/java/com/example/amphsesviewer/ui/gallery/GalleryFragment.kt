package com.example.amphsesviewer.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
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
import com.example.amphsesviewer.feature.gallery.viewmodel.*
import com.example.amphsesviewer.ui.adapters.GalleryAdapter
import com.example.amphsesviewer.ui.diffutils.ImageDiffUtilCallback
import java.lang.ref.SoftReference
import javax.inject.Inject


class GalleryFragment : Fragment(), IGallery {

    private val longClickCallback: () -> Unit = {
        viewModel(GalleryEvent.SetEditMode)
    }

    private val editItemClickHandler = object : IGallery.EditItemClickHandler {
        override fun setSelected(itemId: Long) {
            checkedIds.add(itemId)
        }

        override fun setUnselected(itemId: Long) {
            checkedIds.remove(itemId)
        }

    }

    private val itemClickCallback = { selectedItemPosition: Int, idList: List<Long> ->
        itemClickHandler(selectedItemPosition, idList)
    }

    private val itemSizeChangedCallback = {
        viewModel(GalleryEvent.ItemSizeCalculated(galleryAdapter.itemWidth, galleryAdapter.itemHeight))
    }

    private var binding: FragmentGalleryBinding? = null

    @Inject
    lateinit var galleryViewModelFactory: GalleryViewModelFactory

    private val viewModel: GalleryViewModel by viewModels { galleryViewModelFactory }
    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        FeatureComponentManager.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val layoutManager = GridLayoutManager(context, 2)
        galleryAdapter = GalleryAdapter(context).apply {
            itemLongClickCallback = this@GalleryFragment.longClickCallback
            itemClickCallback = this@GalleryFragment.itemClickCallback
            editItemClickHandler = this@GalleryFragment.editItemClickHandler
            itemSizeChangedCallback = this@GalleryFragment.itemSizeChangedCallback
        }
        viewModel.run {

            action.observe(viewLifecycleOwner, Observer {
                processAction(it)
            })
            viewState.observe(viewLifecycleOwner, Observer {
                render(it)
            })
        }
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding?.apply {
            this.btnLoad.setOnClickListener { viewModel(GalleryEvent.LoadClicked) }
            this.btnDelete.setOnClickListener { viewModel(GalleryEvent.DeleteClicked(galleryAdapter.checkedIds.toList())) }
            this.rvImages.layoutManager = layoutManager
            this.rvImages.adapter = galleryAdapter

            val globalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    (rvImages.layoutManager as GridLayoutManager).let {
                        if (it.childCount > 0) {
                            it.getChildAt(0)?.run {
                                galleryAdapter.setItemSize(width, height)
                                viewModel(GalleryEvent.ItemSizeCalculated(galleryAdapter.itemWidth, galleryAdapter.itemHeight))
                            }
                            rvImages.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    }
                }
            }

            this.rvImages.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        }?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun render(viewState: GalleryState) {
        when (viewState.mode) {
            GalleryMode.View -> {
                galleryAdapter.isEditEnabled = false
                galleryAdapter.itemLongClickCallback = this@GalleryFragment.longClickCallback

                binding?.run {
                    btnDelete.visibility = View.GONE
                    btnLoad.visibility = View.VISIBLE
                }
            }
            GalleryMode.Edit -> {
                binding?.run {
                    btnLoad.visibility = View.GONE
                    btnDelete.visibility = View.VISIBLE
                }
                galleryAdapter.run {
                    isEditEnabled = true
                    itemLongClickCallback = {}
                }
            }
        }

        galleryAdapter.run{
            val imageList: List<ImageUI> = viewState.imagesMap.toSortedMap().map {
                ImageUI(it.key, SoftReference(it.value))
            }
            val diffUtilCallback = ImageDiffUtilCallback(images, imageList)
            val result = DiffUtil.calculateDiff(diffUtilCallback)
            images = imageList
            result.dispatchUpdatesTo(this)
            if (diffUtilCallback.newListSize > diffUtilCallback.oldListSize) {
                viewModel(GalleryEvent.ItemsAdded(galleryAdapter.itemWidth, galleryAdapter.itemHeight))
            }
        }
    }

    private fun processAction(action: GalleryAction) = when (action) {
        is GalleryAction.OpenImageLoader -> findNavController().navigate(R.id.action_albumFragment_to_loadImage)
        is GalleryAction.ShowError -> Toast.makeText(context, action.t.message, Toast.LENGTH_LONG).show()
    }

    override fun loadImages(ids: List<Long>?) {
        if (ids != null)
            viewModel(GalleryEvent.LoadImages(ids))
        else
            viewModel(GalleryEvent.LoadAllImages)
    }

    override fun loadAllImages() {
        viewModel(GalleryEvent.LoadAllImages)
    }

    override var mode: GalleryMode = GalleryMode.View
        set(value) {
            field = value
            when(value) {
                GalleryMode.Edit -> { viewModel(GalleryEvent.SetEditMode) }
                GalleryMode.View -> { viewModel(GalleryEvent.SetViewMode) }
            }
        }

    override val checkedIds = HashSet<Long>()

    override lateinit var itemClickHandler: (selectedItemPosition: Int, idList: List<Long>) -> Unit
}
