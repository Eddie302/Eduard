package com.example.amphsesviewer.ui.album

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.example.amphsesviewer.R
import com.example.amphsesviewer.databinding.FragmentAlbumBinding
import com.example.amphsesviewer.feature.album.viewmodel.*
import com.example.amphsesviewer.feature.di.FeatureComponentManager
import com.example.amphsesviewer.ui.gallery.GalleryMode
import com.example.amphsesviewer.ui.gallery.IGallery
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class AlbumFragment : Fragment() {

    private var binding: FragmentAlbumBinding? = null

    private val args: AlbumFragmentArgs by navArgs()

    var gallery: IGallery? = null

    @Inject
    lateinit var albumViewModelFactory: AlbumViewModelFactory

    private val viewModel: AlbumViewModel by viewModels { albumViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        FeatureComponentManager.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.run {
            viewState.observe(viewLifecycleOwner, Observer { render(it) })
        }

        binding = FragmentAlbumBinding.inflate(inflater, container, false).apply {
            btnEdit.setOnClickListener {
                viewModel(AlbumEvent.SetEditMode)
            }
            btnSave.setOnClickListener {
                gallery?.run {
                    viewModel(AlbumEvent.SaveImages(args.id, checkedIds.toList()))
                }
            }
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gallery = childFragmentManager.findFragmentById(R.id.fragmentGallery) as? IGallery
        gallery?.itemClickHandler = this::navigateToImageViewer
    }

    private fun render(state: AlbumState) {
        when (state.mode) {
            AlbumMode.View -> {
                binding?.run {
                    btnEdit.visibility = View.GONE
                    btnSave.visibility = View.VISIBLE
                }
                gallery?.run {
                    mode = GalleryMode.View
                    loadImages(args.imageIds?.toList())
                }
            }
            AlbumMode.Edit -> {
                binding?.run {
                    btnSave.visibility = View.GONE
                    btnEdit.visibility = View.VISIBLE
                }
                gallery?.run {
                    mode = GalleryMode.Edit
                    loadAllImages()
                }
            }
        }
    }

    private fun navigateToImageViewer(selectedItemPosition: Int, imageIds: List<Long>) {
        val action = AlbumFragmentDirections.actionAlbumFragmentToImageViewerFragment(
            selectedItemPosition,
            imageIds.toLongArray()
        )
        findNavController().navigate(action)
    }

}
