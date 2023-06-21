package com.example.playlistmaker.presentation.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.data.SerializatorTrack
import com.example.playlistmaker.domain.api.Serializator
import com.example.playlistmaker.domain.models.StateSearch
import com.example.playlistmaker.presentation.media.ActivityMedia
import com.example.playlistmaker.presentation.viewmodels.SearchViewModel
import com.example.playlistmaker.presentation.viewmodels.SearchViewModelFactory

class ActivitySearch : AppCompatActivity() {

    companion object {
        private const val DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE = 1000L
    }

    private var text: String = ""
    private var musicAdapter = MusicAdapter()

    private var isClick = true
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        progressBar.isGone = false
        viewModel.uploadTracks(text) }
    private lateinit var nothingSearch: LinearLayout
    private lateinit var noConnection: LinearLayout
    lateinit var search: EditText
    lateinit var clear: ImageView
    private lateinit var recycler: RecyclerView
    private lateinit var refreshButton: Button
    private lateinit var clearHistory: Button
    private lateinit var textClear: TextView
    private lateinit var progressBar: ProgressBar

    private val serializatorTrack: Serializator = SerializatorTrack()

    // VIEWMODEL
    private lateinit var viewModel: SearchViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val sharedPreferences = getSharedPreferences("SearchActivity", MODE_PRIVATE)
        viewModel = ViewModelProvider(
            this, SearchViewModelFactory(
                sharedPreferences,
            )
        )[SearchViewModel::class.java]

        viewModel.getState().observe(this) {

            when (it.second) {
                StateSearch.SHOW_UPLOAD_TRACKS -> {
                    progressBar.isGone = true
                    recycler.visibility = View.VISIBLE
                    nothingSearch.isGone = true
                    noConnection.isGone = true
                    clearHistory.isGone = true
                    textClear.isGone = true
                    musicAdapter.music = it.first!!
                    musicAdapter.notifyDataSetChanged()
                }
                StateSearch.EMPTY_UPLOAD_TRACKS -> {
                    nothingSearch.visibility = View.VISIBLE
                    noConnection.isGone = true
                    progressBar.isGone = true
                    clearHistory.isGone = true
                    textClear.isGone = true
                    recycler.visibility = View.INVISIBLE
                }
                StateSearch.NO_CONNECTION -> {
                    noConnection.visibility = View.VISIBLE
                    progressBar.isGone = true
                    clearHistory.isGone = true
                    textClear.isGone = true
                    recycler.visibility = View.INVISIBLE
                }
                StateSearch.SHOW_HISTORY -> {
                    if (it.first?.isEmpty() == false) {
                        clearHistory.visibility = View.VISIBLE
                        recycler.visibility = View.VISIBLE
                        textClear.visibility = View.VISIBLE
                        musicAdapter.music = it.first!!
                        musicAdapter.notifyDataSetChanged()
                    }
                }
                StateSearch.EMPTY_HISTORY -> {
                    clearHistory.isGone = true
                    textClear.isGone = true
                }
                else -> {
                    // default state
                    progressBar.isGone = true
                    nothingSearch.isGone = true
                    noConnection.isGone = true
                    clearHistory.isGone = true
                }
            }
        }

        nothingSearch = findViewById(R.id.nothingSearch)
        noConnection = findViewById(R.id.nothingConnection)
        search = findViewById(R.id.edit_search)
        clear = findViewById(R.id.clear_search)
        recycler = findViewById(R.id.musicList)
        refreshButton = findViewById(R.id.refreshButton)
        clearHistory = findViewById(R.id.clearHistory)
        textClear = findViewById(R.id.historySearch)
        progressBar = findViewById(R.id.progressBar)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = musicAdapter

        if (savedInstanceState != null) {
            text = savedInstanceState.getString("textSearch").toString()
            if (!text.isNullOrEmpty()) search.setText(text)
        }

        search.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel.getHistory()
        }

        musicAdapter.itemClickListener = { _, track ->

            viewModel.removeTrack(track)

            // Переход на экран плеера
            if (track != null) {
                val intentMedia = Intent(this, ActivityMedia::class.java)
                intentMedia.putExtra("track", serializatorTrack.trackToJSON(track))
                startActivity(intentMedia)
            }
        }

        refreshButton.setOnClickListener { viewModel.uploadTracks(text) }

        findViewById<ImageView>(R.id.backSettings).setOnClickListener { finish() }

        search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.uploadTracks(text)
            }
            false
        }

        clearHistory.setOnClickListener {
            if (clickDebounse()) {
                viewModel.clear()
                musicAdapter.music = ArrayList()
                musicAdapter.notifyDataSetChanged()
                clearHistory.isGone = true
                textClear.isGone = true
                viewModel.setHistory()
            }
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                visibleInvisibleClearButton(search, clear)
                text = p0.toString()
                searchDebounse()
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        search.addTextChangedListener(simpleTextWatcher)
        clear.setOnClickListener {
            search.text.clear()
            nothingSearch.isGone = true
            noConnection.isGone = true
            viewModel.getHistory()
            recycler.adapter = musicAdapter
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            visibleInvisibleClearButton(search, clear)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("textSearch", text)
    }

    private fun visibleInvisibleClearButton(search: EditText, clear: ImageView) {
        clear.isVisible = search.text.isNotEmpty()
    }

    private fun searchDebounse() {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, DEBOUNCE_DELAY)
    }

    private fun clickDebounse(): Boolean {
        val current = isClick
        if (isClick) {
            isClick = false
            handler.postDelayed({ isClick = true }, CLICK_DEBOUNCE)
        }
        return current
    }

    override fun onResume() {
        super.onResume()
    }

}



