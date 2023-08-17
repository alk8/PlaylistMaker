package com.example.playlistmaker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentFavoriteBinding
import com.example.playlistmaker.presentation.player.PlayerFragment
import com.example.playlistmaker.presentation.search.MusicAdapter
import com.example.playlistmaker.presentation.viewmodels.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private var musicAdapter = MusicAdapter()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    val viewModel: FavoriteViewModel by viewModel()

    companion object {
        fun newInstance() = FavoriteFragment().apply {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoriteTracks().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.text.text = getString(R.string.emptyMediateka)
                binding.musicList.isGone = true
            } else {
                binding.text.isGone = true
                binding.imageView.isGone = true
                recycler = _binding!!.musicList
                musicAdapter.music = it
                recycler.layoutManager = LinearLayoutManager(binding.root.context)
                recycler.adapter = musicAdapter
            }
        }

        musicAdapter.itemClickListener = { _, track ->
            // Переход на экран плеера
            findNavController().navigate(
                R.id.action_favoriteFragment_to_playerFragment,
                PlayerFragment.createArgs(viewModel.trackToJSON(track))
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}