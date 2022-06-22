package cl.stgoneira.ejemploespresso.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cl.stgoneira.ejemploespresso.databinding.ActivityLibrosBinding
import cl.stgoneira.ejemploespresso.ui.recycler.adapter.LibrosAdapter
import cl.stgoneira.ejemploespresso.ui.viewmodel.LibrosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LibrosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLibrosBinding

    private val  librosViewModel: LibrosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = LibrosAdapter(emptyList())

        librosViewModel.cargarLibros()

        librosViewModel.libros.observe(this) { libros ->
            binding.recyclerview.adapter = LibrosAdapter(libros)
        }
    }
}