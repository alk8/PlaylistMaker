package com.example.playlistmaker.presentation.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.domain.models.states.StateSearch
import com.example.playlistmaker.presentation.player.PlayerFragment
import com.example.playlistmaker.presentation.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private var text: String = ""
    private var musicAdapter = MusicAdapter()
    private var isClick = true
    private lateinit var nothingSearch: LinearLayout
    private lateinit var noConnection: LinearLayout
    lateinit var search: EditText
    lateinit var clear: ImageView
    private lateinit var recycler: RecyclerView
    private lateinit var refreshButton: Button
    private lateinit var clearHistory: Button
    private lateinit var textClear: TextView
    private lateinit var progressBar: ProgressBar
    private val runnable = Runnable {
        progressBar.isGone = false
        viewModel.uploadTracks(text)
    }
    private val viewModel: SearchViewModel by viewModel {
        parametersOf(runnable)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nothingSearch = binding.nothingSearch
        noConnection = binding.nothingConnection
        search = binding.editSearch
        clear = binding.clearSearch
        recycler = binding.musicList
        refreshButton = binding.refreshButton
        clearHistory = binding.clearHistory
        textClear = binding.historySearch
        progressBar = binding.progressBar

        viewModel.getState().observe(viewLifecycleOwner) {

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
                        progressBar.isGone = true
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


        recycler.layoutManager = LinearLayoutManager(binding.root.context)
        recycler.adapter = musicAdapter

        if (savedInstanceState != null) {
            text = savedInstanceState.getString("textSearch").toString()
            if (text.isNotEmpty()) search.setText(text)
        }

        search.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel.getHistory()
        }

        musicAdapter.itemClickListener = { _, track ->
            viewModel.removeTrack(track)
            // Переход на экран плеера

            findNavController().navigate(
                R.id.action_searchFragment_to_playerFragment,
                PlayerFragment.createArgs(viewModel.trackToJSON(track))
            )
        }

        refreshButton.setOnClickListener { viewModel.uploadTracks(text) }

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
                if (text.isNotEmpty()) {
                    searchDebounse()
                }
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
//            this.currentFocus?.let { view ->
//                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
//                imm?.hideSoftInputFromWindow(view.windowToken, 0)
//            }
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
        viewModel.searchDebounse()
    }

    private fun clickDebounse(): Boolean {
        val current = isClick
        if (isClick) {
            isClick = false
            isClick = viewModel.clickDebounse()
        }
        return current
    }
}