package cl.aiep.android.anchorbooks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import cl.aiep.android.anchorbooks.adapter.LIBROID_MESSAGE
import cl.aiep.android.anchorbooks.databinding.ActivityDetalleBinding
import cl.aiep.android.anchorbooks.view.LibroDetalleViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetalleBinding

    private val libroDetalleViewModel:LibroDetalleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val libroid = intent.getIntExtra(LIBROID_MESSAGE, 0)

        // le delegamos la responsabilidad al ViewModel
        // para cargar los datos del libro
        libroDetalleViewModel.cargarLibro(libroid)

        libroDetalleViewModel.libro.observe(this, Observer { libro ->
            with(binding) {
                tvDetalleTitulo.text = libro.titulo
                tvDetalleAutor.text = libro.autor
                tvDetallePrecio.text = "${libro.precio}"
                Picasso.get().load(libro.imagen).into(imgDetalle)
            }

            // evento boton comprar
            binding.btnComprar.setOnClickListener {
                val textoCorreo = """
                Hola, 
                    
                Vi el libro ${libro.titulo} de código ${libro.id} y me gustaría que me 
                contactaran a este correo o al siguiente número telefónico ___________.
                
                Quedo atent@
                """.trimIndent()

                val intentMail = Intent(Intent.ACTION_SENDTO).apply {
                    type = "msage/rfc822" // fix para que funcione PARA (TO)
                    data = Uri.parse("mailto:")
                    val para:Array<String> = arrayOf("ventas@anchorbook.cl")

                    putExtra(Intent.EXTRA_EMAIL, para)
                    putExtra(Intent.EXTRA_SUBJECT, "Consulta por libro ${libro.titulo} de código ${libro.id}")
                    putExtra(Intent.EXTRA_TEXT, textoCorreo)
                }

                startActivity(intentMail)
            }
            // END evento boton comprar
        })
    }
}