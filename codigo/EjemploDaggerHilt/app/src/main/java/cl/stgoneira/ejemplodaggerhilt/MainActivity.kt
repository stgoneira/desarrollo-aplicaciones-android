package cl.stgoneira.ejemplodaggerhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import cl.stgoneira.ejemplodaggerhilt.data.entity.ProductoEntity
import cl.stgoneira.ejemplodaggerhilt.data.repo.ProductoRepository
import cl.stgoneira.ejemplodaggerhilt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var productoRepository: ProductoRepository

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // IO para trabajo de Entrada/Salida
        lifecycleScope.launch(Dispatchers.IO) {
            // acá se puede utilizar la variable inyectada
            productoRepository.borrarTodo()

            productoRepository.crear(ProductoEntity(1, "Plato", 2_000.0))
            productoRepository.crear(ProductoEntity(2, "Cuchara", 500.0))

            val productos = productoRepository.findAll()

            // Main para escribir en UI
            withContext(Dispatchers.Main) {
                binding.textview.text = productos.toString()
            }
        }
    }
}