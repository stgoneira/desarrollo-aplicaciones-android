package cl.stgoneira.ejemplomvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cl.stgoneira.ejemplomvvm.databinding.ActivityMainBinding
import cl.stgoneira.ejemplomvvm.view.adapter.LibroAdapter
import cl.stgoneira.ejemplomvvm.viewmodel.LibroViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private val libroViewModel : LibroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView( binding.root )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        libroViewModel.cargarLibros()

        libroViewModel.libros.observe(this, Observer { libros ->
            binding.recyclerView.adapter = LibroAdapter(libros)
        })
    }
}