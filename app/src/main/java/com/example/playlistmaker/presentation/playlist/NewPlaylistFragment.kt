package com.example.playlistmaker.presentation.playlist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentNewplaylistBinding
import com.example.playlistmaker.presentation.viewmodels.NewPlaylistViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.util.*

class NewPlaylistFragment : Fragment() {

    private var uriFile: Uri = Uri.EMPTY
    private lateinit var binding: FragmentNewplaylistBinding
    private val viewModel: NewPlaylistViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewplaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Открытие фотогалереи
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->

                if (uri != null) {

                    val filePath = File(
                        requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "albums"
                    )

                    if (!filePath.exists()) {
                        filePath.mkdirs()
                    }

                    val name = binding.nameAlbum.text.ifEmpty {
                        UUID.randomUUID().toString()
                    }

                    val file = File(filePath, "$name.jpg")
                    val inputStream = requireActivity().contentResolver.openInputStream(uri)
                    val outputStream = FileOutputStream(file)

                    BitmapFactory.decodeStream(inputStream)
                        .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)

                    uriFile = file.toUri()

                    Glide.with(binding.album).load(uriFile).centerCrop()
                        .placeholder(R.drawable.ic_noconnection).transform(RoundedCorners(15))
                        .into(binding.album)

                }

            }

        binding.back.setOnClickListener {
            if (checkAlbumDialogue()) {
                showDialogue()
            } else {
                findNavController().popBackStack()
            }
        }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.create.isEnabled = s.toString().isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        binding.nameAlbum.addTextChangedListener(watcher)

        binding.album.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.create.setOnClickListener {
            createAlbum()
        }
    }

    private fun checkAlbumDialogue(): Boolean {
        return binding.nameAlbum.text.isNotEmpty() || binding.description.text.isNotEmpty()
    }

    private fun showDialogue() {

        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Завершить создание плейлиста?\"")
            .setMessage("Все несохранненые данные будут потеряны")
            .setNegativeButton("Отмена") { _, _ ->
            }.setPositiveButton("Завершить") { _, _ ->
                findNavController().popBackStack()
            }.show()
    }

    private fun createAlbum() {

        if (binding.nameAlbum.text.isNotEmpty() || binding.description.text.isNotEmpty()) {
            // Создание записи в БД по всем необходимым данным
            val nameAlbum = binding.nameAlbum.text.toString()
            val description = binding.description.text.toString()

            viewModel.saveAlbum(nameAlbum, description, uriFile)
            Toast.makeText(requireContext(), "Альбом $nameAlbum создан", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                this.context,
                "Не заполнены поля Название и описание",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}