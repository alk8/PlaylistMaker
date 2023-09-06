package com.example.playlistmaker.presentation.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentShowAlbumBinding
import com.example.playlistmaker.presentation.viewmodels.ShowAlbumViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowAlbumFragment : Fragment() {

    private var _binding: FragmentShowAlbumBinding? = null
    private val binding get() = _binding
    private val viemModel: ShowAlbumViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentShowAlbumBinding.inflate(inflater,container,false)

        return binding?.root

    }

}