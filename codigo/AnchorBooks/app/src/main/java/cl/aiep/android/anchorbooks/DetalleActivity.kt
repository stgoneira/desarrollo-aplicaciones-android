package cl.aiep.android.anchorbooks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import cl.aiep.android.anchorbooks.adapter.LIBROID_MESSAGE
import cl.aiep.android.anchorbooks.databinding.ActivityDetalleBinding
import cl.aiep.android.anchorbooks.view.LibroDetalleViewModel
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class DetalleActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetalleBinding

    private val libroDetalleViewModel: LibroDetalleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val libroId = intent.getIntExtra(LIBROID_MESSAGE, 0)

        libroDetalleViewModel.cargarLibro(libroId)

        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.currency = Currency.getInstance("CLP")
        numberFormat.maximumFractionDigits = 0

        libroDetalleViewModel.libro.observe(this, Observer {    libro ->
            with(binding) {
                tvDetalleTitulo.text = libro.titulo
                tvDetalleAutor.text = libro.autor
                tvDetallePrecio.text = numberFormat.format(libro.precio)
                Picasso.get().load(libro.imagen).into(imgDetalleLibro)

                btnDetalleComprar.setOnClickListener {
                    val textoCorreo = """
                        Hola,
                        
                        Vi el libro ${libro.titulo} de código ${libro.id} y me gustaría que me contactaran a este correo o al siguiente número _________.
                        
                        Quedo atento.
                    """.trimIndent()
                    val intentMail = Intent(Intent.ACTION_SENDTO).apply {
                        type = "msage/rfc822"
                        data = Uri.parse("mailto:")
                        val para:Array<String> = arrayOf("ventas@anchorbooks.cl")
                        putExtra(Intent.EXTRA_EMAIL, para)
                        putExtra(Intent.EXTRA_SUBJECT, "Consulta por libro ${libro.titulo} id ${libro.id}")
                        putExtra(Intent.EXTRA_TEXT, textoCorreo)
                    }

                    startActivity(intentMail)

                    if(intentMail.resolveActivity(packageManager) != null) {
                        startActivity(intentMail)
                        Log.d("LIBRO_COMPRAR", "Abriendo app de correos....")
                    } else {
                        Toast.makeText(baseContext, "NO tienen ninguna app de correos instalada!!!", Toast.LENGTH_LONG).show()
                        Log.d("LIBRO_COMPRAR", "No hay ninguna app de correo instalada")
                    }
                }
            }
        })

    }
}