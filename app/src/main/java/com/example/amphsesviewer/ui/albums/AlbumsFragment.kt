package com.example.amphsesviewer.ui.albums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.example.amphsesviewer.R
import com.example.amphsesviewer.databinding.FragmentAlbumsBinding
import com.example.amphsesviewer.feature.albums.viewmodel.AlbumsViewModel
import com.example.amphsesviewer.feature.albums.viewmodel.AlbumsViewModelFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class AlbumsFragment : Fragment() {

    @Inject
    lateinit var albumsViewModelFactory: AlbumsViewModelFactory

    private val viewModel: AlbumsViewModel by viewModels { albumsViewModelFactory }

    private var binding: FragmentAlbumsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAlbumsBinding.inflate(inflater, container, false).apply {

        }
        return binding?.root
    }



    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}
