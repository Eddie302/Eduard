package com.example.amphsesviewer.ui.albums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amphsesviewer.R

import com.example.amphsesviewer.databinding.FragmentAlbumsBinding
import com.example.amphsesviewer.feature.albums.viewmodel.*
import com.example.amphsesviewer.feature.di.FeatureComponentManager
import com.example.amphsesviewer.ui.adapters.AlbumsAdapter
import com.example.amphsesviewer.ui.diffutils.AlbumsDiffUtilCallback
import com.google.android.material.textfield.TextInputEditText
import javax.inject.Inject


class AlbumsFragment : Fragment() {

    @Inject
    lateinit var albumsViewModelFactory: AlbumsViewModelFactory

    private val viewModel: AlbumsViewModel by viewModels { albumsViewModelFactory }

    private lateinit var albumsAdapter: AlbumsAdapter

    private var binding: FragmentAlbumsBinding? = null

    private val itemClickCallback = { imageIds: List<Long> -> navigateToAlbum(imageIds) }

    private fun navigateToAlbum(imageIds: List<Long>? = null) {
        val action = AlbumsFragmentDirections.actionNavAlbumsToAlbumFragment(
            imageIds?.toLongArray()
        )
        findNavController().navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        FeatureComponentManager.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        albumsAdapter = AlbumsAdapter(context).apply {
            itemClickCallback = this@AlbumsFragment.itemClickCallback
        }

        viewModel.run {
            action.observe(viewLifecycleOwner, Observer { processAction(it) })
            viewState.observe(viewLifecycleOwner, Observer { render(it) })
        }

        binding = FragmentAlbumsBinding.inflate(inflater, container, false).apply {
            rvAlbums.layoutManager = LinearLayoutManager(context)
            rvAlbums.adapter = albumsAdapter
            btnNewAlbum.setOnClickListener { viewModel(AlbumsEvent.NewAlbumClicked) }
            btnAllImages.setOnClickListener { navigateToAlbum() }
        }

        return binding?.root
    }

    private fun render(viewState: AlbumsState) {
        albumsAdapter.run {
            val newAlbums = viewState.albums
            val diffUtilCallback = AlbumsDiffUtilCallback(albums, newAlbums)
            val result = DiffUtil.calculateDiff(diffUtilCallback)
            albums = newAlbums
            result.dispatchUpdatesTo(this)
        }
    }

    private fun processAction(action: AlbumsAction) {
        when(action) {
            is AlbumsAction.ShowError -> Toast.makeText(context, action.t.message, Toast.LENGTH_LONG).show()
            is AlbumsAction.OpenNewAlbumCreator -> openNewAlbumDialog()
        }
    }

    private fun openNewAlbumDialog() {
        val dialog: AlertDialog? = context?.let {
            val view = layoutInflater.inflate(R.layout.dialog_new_album, null)
            AlertDialog.Builder(it).run {
                setView(view)
                setTitle("new album")
                setPositiveButton(R.string.dialog_ok) { dialog, _ ->
                    viewModel(AlbumsEvent.CreateNewAlbumClicked(view.findViewById<TextInputEditText>(R.id.edit_album_name).text.toString()))
                    dialog.dismiss()
                }
                setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
                    dialog.dismiss()
                }
            }.create()
        }
        dialog?.show()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}