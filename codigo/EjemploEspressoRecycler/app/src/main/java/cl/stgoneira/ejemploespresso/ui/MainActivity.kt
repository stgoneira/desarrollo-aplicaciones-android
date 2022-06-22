package cl.stgoneira.ejemploespresso.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cl.stgoneira.ejemploespresso.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            binding.textview.text = "Texto de Salida"
        }

        binding.btnVerLibros.setOnClickListener {
            val intent = Intent(this, LibrosActivity::class.java)
            startActivity(intent)
        }
    }
}