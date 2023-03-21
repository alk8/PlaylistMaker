package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class ActivitySearch : AppCompatActivity() {

    var text: String? = ""
    var musicAdapter = MusicAdapter()

    // Инициализация подключения
    private val retrofit = Retrofit.Builder().baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    //Инициализация API
    private val musicAPI = retrofit.create<SearchAPI>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val search = findViewById<EditText>(R.id.edit_search)
        val clear = findViewById<ImageView>(R.id.clear_search)
        val recycler = findViewById<RecyclerView>(R.id.musicList)
        val nothingSearch = findViewById<LinearLayout>(R.id.nothingSearch)
        val noConnection = findViewById<LinearLayout>(R.id.nothingConnection)
        val refreshButton = findViewById<Button>(R.id.refreshButton)
        val clearHistory = findViewById<Button>(R.id.clearHistory)
        val textClear = findViewById<TextView>(R.id.historySearch)
        val type = object : TypeToken<List<Track?>?>() {}.type
        val sharedPreferences = getSharedPreferences("SearchActivity", MODE_PRIVATE)
        val gson = Gson()

        var historyTrack = gson.fromJson<ArrayList<Track>>(
            sharedPreferences.getString("tracksHistory", null),
            type
        )

        if (historyTrack == null) {
            historyTrack = ArrayList()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = musicAdapter

        if (savedInstanceState != null) {
            text = savedInstanceState.getString("textSearch")
            if (!text.isNullOrEmpty()) {
                search.setText(text)
            }
        }

        clearHistory.setOnClickListener {
            historyTrack.clear()
            musicAdapter.music = historyTrack
            clearHistory.visibility = View.INVISIBLE
            textClear.visibility = View.INVISIBLE
            sharedPreferences.edit().putString("tracksHistory", Gson().toJson(historyTrack, type))
                .apply()
        }

        search.setOnFocusChangeListener { _, hasFocus ->
            //Видимость элементов
            if (hasFocus) {

                if (historyTrack.size > 0) {
                    clearHistory.visibility = View.VISIBLE
                    textClear.visibility = View.VISIBLE
                    musicAdapter.music = historyTrack
                }
            }
            else{
                clearHistory.visibility = View.INVISIBLE
                textClear.visibility = View.INVISIBLE
            }
        }

        musicAdapter.itemClickListener = { _, track ->
            historyTrack.remove(track)
            historyTrack.add(0, track)

            if (historyTrack.size > 10) {
                historyTrack.removeLast()
            }
            sharedPreferences.edit().putString("tracksHistory", Gson().toJson(historyTrack, type))
                .apply()

            // Переход на экран плеера
            val intentMedia = Intent(this, ActivityMedia::class.java)
            intentMedia.putExtra("track",gson.toJson(track))
            startActivity(intentMedia)
        }

        refreshButton.setOnClickListener {
            evaluateRequest(noConnection, nothingSearch)
        }

        search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // ВЫПОЛНЯЙТЕ ПОИСКОВЫЙ ЗАПРОС ЗДЕСЬ
                evaluateRequest(noConnection, nothingSearch)
                true
            }
            false
        }

        clearHistory.setOnClickListener {
            historyTrack.clear()
            musicAdapter.music = historyTrack
            musicAdapter.notifyDataSetChanged()
            clearHistory.visibility = View.INVISIBLE
            textClear.visibility = View.INVISIBLE
            sharedPreferences.edit().putString("tracksHistory", Gson().toJson(historyTrack, type))
                .apply()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                visibleInvisibleClearButton(search, clear)
                text = p0.toString()
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

    private fun evaluateRequest(
        noConnection: LinearLayout,
        nothingSearch: LinearLayout,
    ) {
        musicAPI.getMusic(text).enqueue(object : Callback<TrackResponse> {
            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                t.printStackTrace()
                // Ошибка сервера
                noConnection.visibility = View.VISIBLE
            }

            override fun onResponse(
                call: Call<TrackResponse>,
                response: Response<TrackResponse>
            ) {
                if (response.isSuccessful) {
                    val trackJSON = response.body()?.results
                    if (trackJSON != null) {
                        if (trackJSON.isNotEmpty()) {
                            nothingSearch.visibility = View.INVISIBLE
                            noConnection.visibility = View.INVISIBLE
                            musicAdapter.music = trackJSON
                            musicAdapter.notifyDataSetChanged()
                        } else {
                            // не дал результатов
                            nothingSearch.visibility = View.VISIBLE
                            noConnection.visibility = View.INVISIBLE
                        }
                    }
                } else {
                    val error = response.errorBody()?.string()
                }
            }
        })
    }
}
