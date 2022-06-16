package cl.aiep.android.anchorbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cl.aiep.android.anchorbooks.adapter.LibroAdapter
import cl.aiep.android.anchorbooks.databinding.ActivityMainBinding
import cl.aiep.android.anchorbooks.view.LibroViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val libroViewModel:LibroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )

        // RECYCLERVIEW
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        libroViewModel.cargarLibros()

        libroViewModel.libros.observe(this, Observer { libros ->
            binding.recyclerView.adapter = LibroAdapter(libros)
        })
    }
}