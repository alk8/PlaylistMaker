package com.example.playlistmaker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlaylistsBinding
import com.example.playlistmaker.presentation.states.StateMediatekaFragment
import com.example.playlistmaker.presentation.viewmodels.EmptyMediatekaFragmentModel
import org.koin.android.ext.android.getKoin

class PlaylistsFragment : Fragment() {

    private var STATE = StateMediatekaFragment.DEFAULT

    companion object {
        fun newInstance(state: StateMediatekaFragment) = PlaylistsFragment().apply {
            STATE = state
        }
    }

    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : EmptyMediatekaFragmentModel = getKoin().get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        when (STATE) {

            StateMediatekaFragment.FAVORITE -> {
                binding.newPlaylist.isGone = true
                binding.text.text = getString(R.string.emptyMediateka)
            }
            StateMediatekaFragment.PLAYLISTS -> {
                binding.text.text = getString(R.string.emptyPlayLists)
            }
            StateMediatekaFragment.DEFAULT -> {

            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}