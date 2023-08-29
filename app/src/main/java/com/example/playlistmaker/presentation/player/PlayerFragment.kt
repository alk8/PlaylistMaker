package com.example.playlistmaker.presentation.player

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlayerBinding
import com.example.playlistmaker.domain.entities.FormatterTime
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.models.states.StateMusicPlayer
import com.example.playlistmaker.presentation.playlist.PlaylistBottomAdapter
import com.example.playlistmaker.presentation.viewmodels.PlayerViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlayerFragment : Fragment() {

    private var isDark = false

    private lateinit var track: Track
    private lateinit var timer: TextView
    private lateinit var play: ImageView
    private var adapter = PlaylistBottomAdapter(arrayListOf())

    private lateinit var binding: FragmentPlayerBinding

    val viewModel: PlayerViewModel by viewModel {
        parametersOf(requireArguments().getString(TRACK))
    }

    companion object {
        const val TRACK = "track"

        fun createArgs(track: String?): Bundle {
            return bundleOf(TRACK to track)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = binding.timer
        play = binding.playButton
        isDark = isDarkTheme()

        play.setOnClickListener { viewModel.controlPlayer() }

        val trackName = binding.trackName
        val artistName = binding.artistName
        val time = binding.time
        val album = binding.albumData
        val year = binding.yearData
        val genre = binding.genreData
        val country = binding.countryData
        val picture = binding.album

        val bottomSheetContainer =
            requireActivity().findViewById<LinearLayout>(R.id.standard_bottom_sheet)
        val recycler = requireActivity().findViewById<RecyclerView>(R.id.recyclerViewAlbum)
        val newPlaylist = requireActivity().findViewById<Button>(R.id.newList)
        recycler.layoutManager = LinearLayoutManager(binding.root.context)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        viewModel.getTimerTextData().observe(viewLifecycleOwner) {
            timer.text = it
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.libraryButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // newState — новое состояние BottomSheet
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        // получаем список альбомов
                        viewModel.getPlaylists()
                    }
                    else -> {
                        // Остальные состояния не обрабатываем
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        viewModel.getStateData().observe(viewLifecycleOwner) {

            if (it == StateMusicPlayer.NO_CONTENT) {
                Toast.makeText(this.context, R.string.warning, Toast.LENGTH_SHORT).show()
            }

            if (isDark) {
                if (it == StateMusicPlayer.PLAYING) {
                    play.setImageResource(R.drawable.pause_nightmode)
                } else {
                    play.setImageResource(R.drawable.white_play_button)
                }

            } else {
                if (it == StateMusicPlayer.PLAYING) {
                    play.setImageResource(R.drawable.pause)
                } else {
                    play.setImageResource(R.drawable.play_button)
                }
            }
        }

        viewModel.getTrackData().observe(viewLifecycleOwner) {

            track = it
            trackName.text = track.trackName
            artistName.text = track.artistName
            time.text = FormatterTime.formatTime(track.trackTimeMillis)
            album.text = track.collectionName
            year.text = FormatterTime.getYear(track.releaseDate)
            genre.text = track.primaryGenreName
            country.text = track.country

            Glide.with(picture).load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                .centerCrop()
                .placeholder(R.drawable.ic_noconnection).transform(RoundedCorners(15))
                .into(picture)

            isFavorite()

        }

        viewModel.getPlaylistData().observe(viewLifecycleOwner) {
            adapter = PlaylistBottomAdapter(it)

            adapter.itemClickListener = { _, album ->
                viewModel.addSongToPlaylist(album, track)
            }
            recycler.adapter = adapter
        }

        viewModel.getIncludedData().observe(viewLifecycleOwner) {

            if (it.first) {
                // Уже включен в альбом
                Toast.makeText(
                    this.context,
                    "Трек уже добавлен в плейлист ${it.second}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // не включен
                Toast.makeText(
                    this.context,
                    "Трек добавлен в плейлист ${it.second}",
                    Toast.LENGTH_SHORT
                ).show()

                //Обновить отображение альбомов
                viewModel.getPlaylists()
            }

        }

        newPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_playerFragment_to_newPlaylistFragment)
        }

        binding.likeButton.setOnClickListener {

            // снять или поставить
            track.isFavorite = !track.isFavorite

            isFavorite()

            if (track.isFavorite) {
                viewModel.setLike()
            } else {
                viewModel.deleteLike()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPlaylists()
    }

    private fun isDarkTheme(): Boolean {
        return this.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    private fun isFavorite() {

        if (track.isFavorite) {
            binding.likeButton.setImageResource(R.drawable.buttonlike)
        } else {
            binding.likeButton.setImageResource(R.drawable.like_button)
        }
    }

}