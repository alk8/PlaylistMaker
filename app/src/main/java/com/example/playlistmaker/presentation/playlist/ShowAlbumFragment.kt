package com.example.playlistmaker.presentation.playlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentShowAlbumBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.player.PlayerFragment
import com.example.playlistmaker.presentation.search.MusicAdapter
import com.example.playlistmaker.presentation.viewmodels.ShowAlbumViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ShowAlbumFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private var _binding: FragmentShowAlbumBinding? = null
    private var musicAdapter = MusicAdapter()
    private val binding get() = _binding
    private val viemModel: ShowAlbumViewModel by viewModel()


    companion object {
        private const val ALBUM = "album"

        fun createArgs(UUID: String): Bundle {
            return bundleOf(ALBUM to UUID)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowAlbumBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var UUIDAlbum = ""
        var tracks: List<Track> = arrayListOf()
        // Получение информации о плейлисте
        requireArguments().getString(ALBUM)?.let { viemModel.getDataAlbum(it) }

        viemModel.getAlbumData().observe(viewLifecycleOwner) {

            binding?.nameAlbum?.text = it.nameAlbum
            binding?.description?.text = it.description
            UUIDAlbum = it.UUID

            if (it.uri != Uri.EMPTY) {

                Glide.with(binding?.photo!!).load(it.uri).centerCrop()
                    .placeholder(R.drawable.ic_noconnection).transform(RoundedCorners(15))
                    .into(binding?.photo!!)

            }
        }

        viemModel.getTracksData().observe(viewLifecycleOwner) {

            recycler = _binding?.recyclerViewTracks!!
            musicAdapter.music = it
            recycler.layoutManager = LinearLayoutManager(_binding!!.root.context)
            recycler.adapter = musicAdapter
            tracks = it

            countAlbums(it)

        }

        musicAdapter.itemClickListener = { _, track ->

            findNavController().navigate(
                R.id.action_showAlbumFragment_to_playerFragment,
                PlayerFragment.createArgs(viemModel.trackToJSON(track))
            )

        }

        musicAdapter.longClick = { _, track ->

            MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Хотите удалить трек?")
                .setNegativeButton("Отмена") { _, _ ->
                }.setPositiveButton("Удалить") { _, _ ->
                    viemModel.deleteTrack(track.artworkUrl100, UUIDAlbum)
                }.show()

            true

        }

        binding?.share?.setOnClickListener {

            if (tracks.isEmpty()) {
                Toast.makeText(
                    context,
                    "В этом плейлисте нет списка треков, которым можно поделиться",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                var text = "${binding!!.nameAlbum.text} \n ${binding!!.countTracks.text} \n"
                var count = 1

                for (t in tracks) {

                    val m = SimpleDateFormat("mm", Locale.getDefault()).format(t.trackTimeMillis?.toInt()
                        ?: 0)
                        .toInt()

                    text += "$count | ${t.artistName} - ${t.trackName} | (${m}) \n"
                    count++

                }

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, text)
                startActivity(intent)
            }
        }
    }

    private fun countAlbums(tracks: List<Track>) {

        var minutes = 0

        tracks.forEach {
            minutes += it.trackTimeMillis?.toInt() ?: 0
        }

        val minut = SimpleDateFormat("mm", Locale.getDefault()).format(minutes).toInt()
        val size = tracks.size

        binding?.minutes?.text = resources.getQuantityString(R.plurals.minutes, minut, minut)
        binding?.countTracks?.text = resources.getQuantityString(R.plurals.tracks, size, size)

    }


}