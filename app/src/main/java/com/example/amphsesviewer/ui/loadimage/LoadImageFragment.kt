package com.example.amphsesviewer.ui.loadimage

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController

import com.example.amphsesviewer.REQUEST_GALLERY
import com.example.amphsesviewer.databinding.LoadImageFragmentBinding
import com.example.amphsesviewer.feature.di.FeatureComponentManager
import com.example.amphsesviewer.feature.loadimage.factory.LoadImageViewModelFactory
import com.example.amphsesviewer.feature.loadimage.viewmodel.LoadImageAction
import com.example.amphsesviewer.feature.loadimage.viewmodel.LoadImageEvent
import com.example.amphsesviewer.feature.loadimage.viewmodel.LoadImageState
import com.example.amphsesviewer.feature.loadimage.viewmodel.LoadImageViewModel
import javax.inject.Inject

class LoadImageFragment : Fragment() {

    @Inject
    lateinit var loadImageViewModelFactory: LoadImageViewModelFactory

    private val viewModel: LoadImageViewModel by viewModels { loadImageViewModelFactory }
    private var binding: LoadImageFragmentBinding? = null

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val uri = intent?.getParcelableExtra<Uri>("imageUri")
            if (uri != null) {
                val bitmap: Bitmap? = decodeBitmap(uri)
                viewModel(LoadImageEvent.ImageSelected(bitmap))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        FeatureComponentManager.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LocalBroadcastManager.getInstance(context!!).registerReceiver(broadcastReceiver, IntentFilter(
            REQUEST_GALLERY.toString()))

        binding = LoadImageFragmentBinding.inflate(inflater, container, false).apply {
            btnLoadImage.setOnClickListener {
                viewModel(LoadImageEvent.LoadClicked)
            }
            btnSave.setOnClickListener {
                viewModel(LoadImageEvent.SaveClicked)
            }
        }
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            action.observe(viewLifecycleOwner, Observer {
                processAction(it)
            })
            viewState.observe(viewLifecycleOwner, Observer {
                renderState(it)
            })
        }
    }

    private fun renderState(state: LoadImageState) {
        val bitmap = state.bitmap
        if (bitmap != null) {
            setSelectedBitmap(bitmap)
        } else {
            clearSelectedBitmap()
        }
    }

    private fun processAction(action: LoadImageAction) {
        when (action) {
            is LoadImageAction.OpenGallery -> {
                val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activity?.startActivityForResult(intent, REQUEST_GALLERY)
            }
            is LoadImageAction.NavigateBack -> findNavController().popBackStack()
        }
    }

    private fun setSelectedBitmap(bitmap: Bitmap) {
        binding?.apply {
            btnLoadImage.visibility = View.GONE
            imgSelected.visibility = View.VISIBLE
            btnSave.visibility = View.VISIBLE
            imgSelected.setImageBitmap(bitmap)
        }
    }

    private fun clearSelectedBitmap() {
        binding?.apply {
            imgSelected.setImageBitmap(null)
            imgSelected.visibility = View.GONE
            btnSave.visibility = View.GONE
            btnLoadImage.visibility = View.VISIBLE
        }
    }

    private fun decodeBitmap(uri: Uri) : Bitmap? {
        val contentResolver = activity?.contentResolver
        return if (contentResolver != null) {
            if(Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            } else {
                val source: ImageDecoder.Source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        } else null
    }
}
