package com.example.playlistmaker.presentation.mediateka

import NumbersViewPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMediatekaBinding
import com.google.android.material.tabs.TabLayoutMediator

class MediatekaActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMediatekaBinding
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediatekaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = NumbersViewPagerAdapter(supportFragmentManager,lifecycle)

        tabMediator = TabLayoutMediator(binding.tabLayout,binding.viewPager) {tab, position ->
            when(position){
                0 -> tab.text = getString(R.string.FavoriteTracks)
                1 -> tab.text = getString(R.string.Playlists)
            }

        }
        tabMediator.attach()
        binding.back.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}