package com.example.playlistmaker.presentation.playlist


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

                    binding.album.setImageURI(uriFile)

                }

            }

        binding.back.setOnClickListener {
            if (checkAlbumDialogue()) {
                findNavController().popBackStack()
            }else{
                showDialogue()
            }
        }

        binding.album.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.create.setOnClickListener {
            createAlbum()
        }
    }

    private fun checkAlbumDialogue(): Boolean {

        if (binding.nameAlbum.text.isEmpty()) return false
        if (binding.description.text.isEmpty()) return false

        return true
    }

    private fun showDialogue() {

        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Внимание")
            .setMessage("Завершить создание плейлиста?")
            .setNegativeButton("Отмена") { _, _ ->
                declineCreateAlbum()
            }.setPositiveButton("Завершить") { _, _ ->
                createAlbum()
            }.show()
    }

    private fun declineCreateAlbum() {
        findNavController().popBackStack()
    }

    private fun createAlbum() {

        if (checkAlbumDialogue()){
            // Создание записи в БД по всем необходимым данным
            val nameAlbum = binding.nameAlbum.text.toString()
            val description = binding.description.text.toString()

            viewModel.saveAlbum(nameAlbum,description,uriFile)
            Toast.makeText(requireContext(),"Альбом $nameAlbum создан",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}