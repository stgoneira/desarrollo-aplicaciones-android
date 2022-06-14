package cl.stgoneira.ejemploviewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cl.stgoneira.ejemploviewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView1.text = "Hola Mundillo Uno!!!"
        binding.textView2.text = "Hola Mundo #2!!!!!"
    }
}