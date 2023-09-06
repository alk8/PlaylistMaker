package com.example.playlistmaker.presentation.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlaylistsBinding
import com.example.playlistmaker.presentation.viewmodels.PlaylistsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : Fragment() {

    companion object {
        fun newInstance() = PlaylistsFragment().apply {
        }
    }

    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : PlaylistsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        binding.text.text = getString(R.string.emptyPlayLists)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        // Получить данные из базы
        viewModel.getPlaylists().observe(viewLifecycleOwner){

            binding.imageView.isGone = it.isNotEmpty()
            binding.text.isGone = it.isNotEmpty()
            var adapter = PlaylistAdapter(it)
            recyclerView.adapter = adapter
            adapter.itemClickListener = {_,album ->

                findNavController().navigate(
                    R.id.action_mediaFragment_to_showAlbumFragment,
                    ShowAlbumFragment.createArgs(album.UUID)
                )
            }
        }

        binding.newPlaylist.setOnClickListener {
            findNavController().navigate(
                R.id.action_mediaFragment_to_newPlaylistFragment
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}