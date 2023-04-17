package com.example.playlistmaker.presentation

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
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.data.AppleAPI
import com.example.playlistmaker.data.repository.SerializatorTrack
import com.example.playlistmaker.data.repository.TrackRepository
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.usecase.SearchTrackUseCase

class ActivitySearch : AppCompatActivity() {

    companion object {
        private const val DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE = 1000L
    }

    private var text: String? = ""
    private var musicAdapter = MusicAdapter()

    private var isClick = true
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable { evaluateRequest() }

    private lateinit var nothingSearch: LinearLayout
    private lateinit var noConnection: LinearLayout
    lateinit var search: EditText
    lateinit var clear: ImageView
    private lateinit var recycler: RecyclerView
    private lateinit var refreshButton: Button
    private lateinit var clearHistory: Button
    private lateinit var textClear: TextView
    private lateinit var progressBar: ProgressBar

    private val appleAPI = AppleAPI()
    private lateinit var trackRepository: TrackRepository
    private val serializatorTrack = SerializatorTrack()

    // CASES
    private val searchTrackUseCase = SearchTrackUseCase()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        trackRepository = TrackRepository(getSharedPreferences("SearchActivity", MODE_PRIVATE))

        nothingSearch = findViewById(R.id.nothingSearch)
        noConnection = findViewById(R.id.nothingConnection)
        search = findViewById(R.id.edit_search)
        clear = findViewById(R.id.clear_search)
        recycler = findViewById(R.id.musicList)
        refreshButton = findViewById(R.id.refreshButton)
        clearHistory = findViewById(R.id.clearHistory)
        textClear = findViewById(R.id.historySearch)
        progressBar = findViewById(R.id.progressBar)

        var historyTrack = trackRepository.getHistory()

        if (historyTrack == null) {
            historyTrack = ArrayList()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = musicAdapter

        if (savedInstanceState != null) {
            text = savedInstanceState.getString("textSearch")
            if (!text.isNullOrEmpty()) search.setText(text)
        }

        clearHistory.setOnClickListener {
            historyTrack.clear()
            musicAdapter.music = historyTrack
            clearHistory.visibility = View.INVISIBLE
            textClear.visibility = View.INVISIBLE
            trackRepository.saveHistory(historyTrack)
        }

        search.setOnFocusChangeListener { _, hasFocus ->
            //Видимость элементов
            if (hasFocus) {
                if (historyTrack.size > 0) {
                    clearHistory.visibility = View.VISIBLE
                    textClear.visibility = View.VISIBLE
                    musicAdapter.music = historyTrack
                }
            } else {
                clearHistory.visibility = View.INVISIBLE
                textClear.visibility = View.INVISIBLE
            }
        }

        musicAdapter.itemClickListener = { _, track ->
            historyTrack.remove(track)
            historyTrack.add(0, track)

            if (historyTrack.size > 10) historyTrack.removeLast()
            trackRepository.saveHistory(historyTrack)

            // Переход на экран плеера
            val intentMedia = Intent(this, ActivityMedia::class.java)
            intentMedia.putExtra("track", serializatorTrack.trackToJSON(track))
            startActivity(intentMedia)
        }

        refreshButton.setOnClickListener { evaluateRequest() }

        search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // ВЫПОЛНЯЙТЕ ПОИСКОВЫЙ ЗАПРОС ЗДЕСЬ
                evaluateRequest()
            }
            false
        }

        clearHistory.setOnClickListener {
            if (clickDebounse()) {
                historyTrack.clear()
                musicAdapter.music = historyTrack
                musicAdapter.notifyDataSetChanged()
                clearHistory.visibility = View.INVISIBLE
                textClear.visibility = View.INVISIBLE
                trackRepository.saveHistory(historyTrack)
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
            nothingSearch.visibility = View.INVISIBLE
            noConnection.visibility = View.INVISIBLE
            musicAdapter.music = ArrayList()
            musicAdapter.notifyDataSetChanged()
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

    @SuppressLint("NotifyDataSetChanged")
    private fun evaluateRequest() {

        searchTrackUseCase.execute(appleAPI, text, object : CallbackResponse {
            override fun onResponse(listTrack: List<Track>) {
                val tracks = listTrack as ArrayList<Track>

                progressBar.visibility = View.VISIBLE
                recycler.visibility = View.INVISIBLE

                if (tracks.isEmpty()) {

                    nothingSearch.visibility = View.VISIBLE
                    noConnection.visibility = View.INVISIBLE
                    progressBar.visibility = View.INVISIBLE

                } else {
                    progressBar.visibility = View.INVISIBLE
                    recycler.visibility = View.VISIBLE
                    nothingSearch.visibility = View.INVISIBLE
                    noConnection.visibility = View.INVISIBLE
                    musicAdapter.music = tracks
                    musicAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure() {
                noConnection.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        })

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

    interface CallbackResponse {
        fun onResponse(listTrack: List<Track>)
        fun onFailure()
    }

}



