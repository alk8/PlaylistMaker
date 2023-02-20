package com.example.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ActivitySearch : AppCompatActivity() {

    var text: String? = ""

    // Инициализация подключения
    private val retrofit = Retrofit.Builder().
        baseUrl("https://itunes.apple.com/").
        addConverterFactory(GsonConverterFactory.create()).
        build()

    //Инициализация API
    private val musicAPI = retrofit.create<SearchAPI>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val search = findViewById<EditText>(R.id.edit_search)
        val clear = findViewById<ImageView>(R.id.clear_search)
        val recycler = findViewById<RecyclerView>(R.id.musicList)
        val nothingSearch = findViewById<LinearLayout>(R.id.nothingSearch)
        recycler.layoutManager = LinearLayoutManager(this)

        if (savedInstanceState != null) {
            text = savedInstanceState.getString("textSearch")
            if (!text.isNullOrEmpty()) {
                search.setText(text)
            }
        }
        search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // ВЫПОЛНЯЙТЕ ПОИСКОВЫЙ ЗАПРОС ЗДЕСЬ
                val response = musicAPI.getMusic(text).enqueue(object : Callback<TrackResponse>{
                    override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                        t.printStackTrace()
                        // Ошибка сервера
                    }
                    override fun onResponse(
                        call: Call<TrackResponse>,
                        response: Response<TrackResponse>
                    ) {
                        if (response.isSuccessful){
                            val trackJSON = response.body()?.results
                            if (trackJSON != null) {
                                if (trackJSON.isNotEmpty()) {
                                    nothingSearch.visibility = View.INVISIBLE
                                    recycler.adapter = MusicAdapter(trackJSON)
                                }else{
                                    // не дал результатов
                                    nothingSearch.visibility = View.VISIBLE
                                }
                            }
                        }else{
                            val error = response.errorBody()?.string()
                        }
                    }
                })
                true
            }
            false
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                visibleInvisibleClearButton(search, clear)
                text = p0.toString()
            }
            override fun afterTextChanged(p0: Editable?) {

            }
        }
        search.addTextChangedListener(simpleTextWatcher)
        clear.setOnClickListener {
            search.text.clear()
            nothingSearch.visibility = View.INVISIBLE
            recycler.adapter = MusicAdapter(arrayListOf())
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

}
