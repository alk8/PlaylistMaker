package com.example.playlistmaker.presentation.playlist

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentShowAlbumBinding
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.search.MusicAdapter
import com.example.playlistmaker.presentation.viewmodels.ShowAlbumViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ShowAlbumFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private var _binding: FragmentShowAlbumBinding? = null
    private var musicAdapter = MusicAdapter()
    private val binding get() = _binding
    private val viemModel: ShowAlbumViewModel by viewModel()

    companion object{
        private const val ALBUM = "album"

        fun createArgs(UUID : String): Bundle {
            return bundleOf(ALBUM to UUID)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentShowAlbumBinding.inflate(inflater,container,false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получение информации о плейлисте
        requireArguments().getString(ALBUM)?.let { viemModel.getDataAlbum(it) }

        viemModel.getAlbumData().observe(viewLifecycleOwner){

            binding?.nameAlbum?.text = it.nameAlbum
            binding?.description?.text = it.description

            if (it.uri != Uri.EMPTY){

                Glide.with(binding?.photo!!).load(it.uri).centerCrop()
                    .placeholder(R.drawable.ic_noconnection).transform(RoundedCorners(15))
                    .into(binding?.photo!!)

            }

        }

        viemModel.getTracksData().observe(viewLifecycleOwner){

            recycler = _binding?.recyclerViewTracks!!
            musicAdapter.music = it
            recycler.layoutManager = LinearLayoutManager(_binding!!.root.context)
            recycler.adapter = musicAdapter

            countAlbums(it)

        }

    }

    private fun countAlbums(tracks:List<Track>){

        var minutes = 0

        tracks.forEach {
            minutes += it.trackTimeMillis?.toInt() ?: 0
        }

        val minut = SimpleDateFormat("mm", Locale.getDefault()).format(minutes)

        binding?.minutes?.text = "$minut минут"
        binding?.countTracks?.text = tracks.size.toString()

    }

    private fun getCount(count: Int): String {

        val preLastDigit: Int = count % 100 / 10

        return if (preLastDigit == 1) {
            "$count треков"
        } else when (count % 10) {
            1 -> "$count трек"
            2, 3, 4 -> "$count трека"
            else -> "$count треков"
        }

    }

}