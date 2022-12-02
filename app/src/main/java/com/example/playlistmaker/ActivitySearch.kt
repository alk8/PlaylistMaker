package com.example.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText

class ActivitySearch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val search = findViewById<EditText>(R.id.edit_search)
        val clear = findViewById<Button>(R.id.clear_search)

        val simpleTextWatcher = object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            visibleInvisibleClearButton(search,clear)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
        search.addTextChangedListener(simpleTextWatcher)

        clear.setOnClickListener {
            search.text.clear()
            visibleInvisibleClearButton(search,clear)
        }

    }

    private fun visibleInvisibleClearButton(search: EditText,clear: Button){

        if (search.text.isEmpty()){
            clear.visibility = View.INVISIBLE
        }else{
            clear.visibility = View.VISIBLE
        }

    }

}