package cl.stgoneira.ejemploespresso.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import cl.stgoneira.ejemploespresso.databinding.ActivityDetalleLibroBinding
import cl.stgoneira.ejemploespresso.ui.recycler.adapter.LIBROID_MESSAGE
import cl.stgoneira.ejemploespresso.ui.viewmodel.DetalleLibroViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleLibroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleLibroBinding

    private val libroViewModel:DetalleLibroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val libroId = intent.getIntExtra(LIBROID_MESSAGE, 0)

        libroViewModel.cargarLibro(libroId)

        libroViewModel.libro.observe(this) { libro ->
            with(binding) {
                tvDetalleTitulo.text = libro.titulo
                tvDetalleAutor.text = libro.autor
                tvDetallePrecio.text = libro.precio.toString()
                Picasso.get().load(libro.imagen).into(imageViewDetalle)
            }
        }
    }
}