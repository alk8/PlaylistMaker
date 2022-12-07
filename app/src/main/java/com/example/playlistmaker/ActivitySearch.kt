package com.example.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class ActivitySearch : AppCompatActivity() {

    companion object{
        var text :String? ="";
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val search = findViewById<EditText>(R.id.edit_search)
        val clear = findViewById<ImageView>(R.id.clear_search)

        if (savedInstanceState != null){

            text = savedInstanceState.getString("textSearch")
            if (!text.isNullOrEmpty()){
                search.setText(text)
            }
        }

        val simpleTextWatcher = object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            visibleInvisibleClearButton(search,clear)
            text = p0.toString();
            }
            override fun afterTextChanged(p0: Editable?) {}
        }

        search.addTextChangedListener(simpleTextWatcher)

        clear.setOnClickListener {
            search.text.clear()
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            visibleInvisibleClearButton(search,clear)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("textSearch", text)
    }


    private fun visibleInvisibleClearButton(search: EditText,clear: ImageView){

        clear.isVisible  =!search.text.isEmpty()

    }

}