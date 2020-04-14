package com.example.amphsesviewer.ui.imageviewer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.example.amphsesviewer.R
import com.example.amphsesviewer.databinding.FragmentImageViewerBinding
import com.example.amphsesviewer.feature.di.FeatureComponentManager
import com.example.amphsesviewer.feature.imageviewer.viewmodel.*
import com.example.amphsesviewer.ui.adapters.ImageViewerAdapter
import javax.inject.Inject


class ImageViewerFragment : Fragment() {
    private val args: ImageViewerFragmentArgs by navArgs()

    private var binding: FragmentImageViewerBinding? = null

    @Inject
    lateinit var imageViewerViewModelFactory: ImageViewerViewModelFactory

    private val viewModel: ImageViewerViewModel by viewModels { imageViewerViewModelFactory }

    private lateinit var imageViewerAdapter: ImageViewerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        FeatureComponentManager.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        imageViewerAdapter = ImageViewerAdapter(context)

        viewModel.run {
            viewState.observe(viewLifecycleOwner, Observer { renderState(it) })
            action.observe(viewLifecycleOwner, Observer { processAction(it) })
        }

        binding = FragmentImageViewerBinding.inflate(inflater, container, false).apply {
            pagerImages.adapter = imageViewerAdapter
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel(ImageViewerEvent.ViewLoaded(args.idList.toList()))
    }

    private fun renderState(state: ImageViewerState) {
        state.images?.let {
            imageViewerAdapter.run {
                images = it
                notifyDataSetChanged()
            }
            binding?.pagerImages?.setCurrentItem(args.selectedItemPosition, false)
        }
    }

    private fun processAction(action: ImageViewerAction) {
        when (action) {
            is ImageViewerAction.ShowLoading -> { binding?.pb?.visibility = View.VISIBLE }
            is ImageViewerAction.HideLoading -> { binding?.pb?.visibility = View.GONE }
            is ImageViewerAction.ShowError -> Toast.makeText(context, action.t.message, Toast.LENGTH_LONG).show()
        }
    }
}
