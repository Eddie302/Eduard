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
import com.example.amphsesviewer.feature.di.FeatureComponentManager
import com.example.amphsesviewer.feature.imageviewer.viewmodel.*
import com.example.amphsesviewer.ui.adapters.ImageViewerAdapter
import javax.inject.Inject


class ImageViewerFragment : Fragment() {

    val args: ImageViewerFragmentArgs by navArgs()

    @Inject
    lateinit var imageViewerViewModelFactory: ImageViewerViewModelFactory

    private val viewModel: ImageViewerViewModel by viewModels { imageViewerViewModelFactory }

    private lateinit var imageViewerAdpter: ImageViewerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        FeatureComponentManager.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        imageViewerAdpter = ImageViewerAdapter(context)

        viewModel.run {
            viewState.observe(viewLifecycleOwner, Observer { renderState(it) })
            action.observe(viewLifecycleOwner, Observer { processAction(it) })
        }
        return inflater.inflate(R.layout.fragment_image_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel(ImageViewerEvent.ViewLoaded(args.idList.toList()))
    }

    private fun renderState(state: ImageViewerState) {
        when (state) {

        }
    }

    private fun processAction(action: ImageViewerAction) {
        when (action) {
            is ImageViewerAction.ShowError -> Toast.makeText(context, action.t.message, Toast.LENGTH_LONG).show()
        }
    }
}
