package com.example.playlistmaker.presentation.player

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlayerBinding
import com.example.playlistmaker.domain.entities.FormatterTime
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.models.states.StateMusicPlayer
import com.example.playlistmaker.presentation.viewmodels.MediaViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlayerFragment : Fragment() {

    private var isDark = false

    private lateinit var track: Track
    private lateinit var timer: TextView
    private lateinit var play: ImageView

    private lateinit var binding: FragmentPlayerBinding

    private val toast = {
        Toast.makeText(this.context, R.string.warning, Toast.LENGTH_SHORT).show()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: MediaViewModel by viewModel {
            parametersOf(requireArguments().getString(TRACK), toast)
        }

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

        viewModel.getTimerTextData().observe(viewLifecycleOwner) {
            timer.text = it
        }

        viewModel.getStateData().observe(viewLifecycleOwner) {

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

        }

    }

    private fun isDarkTheme(): Boolean {
        return this.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}