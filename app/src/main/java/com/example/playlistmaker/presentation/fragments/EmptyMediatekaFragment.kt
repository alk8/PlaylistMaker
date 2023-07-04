package com.example.playlistmaker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentEmptyMediatekaBinding
import com.example.playlistmaker.domain.models.states.StateMediatekaFragment

class EmptyMediatekaFragment(private val state: StateMediatekaFragment) : Fragment() {

    private var _binding: FragmentEmptyMediatekaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmptyMediatekaBinding.inflate(inflater, container, false)

        when (state) {

            StateMediatekaFragment.FAVORITE -> {
                binding.newPlaylist.isGone = true
                binding.text.text = getString(R.string.emptyMediateka)
            }
            StateMediatekaFragment.PLAYLISTS -> {
                binding.text.text = getString(R.string.emptyPlayLists)
            }
            StateMediatekaFragment.DEFAULT ->{

            }
        }
        return binding.root
    }
}