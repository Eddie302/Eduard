package com.example.amphsesviewer.ui.albums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.amphsesviewer.databinding.FragmentAlbumsBinding
import com.example.amphsesviewer.feature.albums.viewmodel.*
import com.example.amphsesviewer.feature.di.FeatureComponentManager
import com.example.amphsesviewer.ui.adapters.AlbumsAdapter
import javax.inject.Inject


class AlbumsFragment : Fragment() {

    @Inject
    lateinit var albumsViewModelFactory: AlbumsViewModelFactory

    private val viewModel: AlbumsViewModel by viewModels { albumsViewModelFactory }

    private lateinit var albumsAdapter: AlbumsAdapter

    private var binding: FragmentAlbumsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        FeatureComponentManager.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        albumsAdapter = AlbumsAdapter(context).apply {

        }

        viewModel.run {
            action.observe(viewLifecycleOwner, Observer { processAction(it) })
            viewState.observe(viewLifecycleOwner, Observer { render(it) })
        }

        binding = FragmentAlbumsBinding.inflate(inflater, container, false).apply {
            rvAlbums.layoutManager = LinearLayoutManager(context)
            rvAlbums.adapter = albumsAdapter
            btnNewAlbum.setOnClickListener { viewModel(AlbumsEvent.NewAlbumClicked) }
        }

        return binding?.root
    }

    private fun render(viewState: AlbumsState) {

    }

    private fun processAction(action: AlbumsAction) {
        when(action) {
            is AlbumsAction.ShowError -> Toast.makeText(context, action.t.message, Toast.LENGTH_LONG).show()
            is AlbumsAction.OpenNewAlbumCreator -> {}//findNavController().navigate()
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}