package com.example.playlistmaker.presentation.mediateka

import NumbersViewPagerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentMediaBinding
import com.google.android.material.tabs.TabLayoutMediator

class MediatekaFragment : Fragment() {

    private lateinit var binding: FragmentMediaBinding
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMediaBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = NumbersViewPagerAdapter(childFragmentManager,lifecycle)
        tabMediator = TabLayoutMediator(binding.tabLayout,binding.viewPager) {tab, position ->
            when(position){
                0 -> tab.text = getString(R.string.FavoriteTracks)
                1 -> tab.text = getString(R.string.Playlists)
            }
        }
        tabMediator.attach()

    }


}