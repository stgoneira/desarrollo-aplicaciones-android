package cl.aiep.android.anchorbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import cl.aiep.android.anchorbooks.adapter.LibroAdapter
import cl.aiep.android.anchorbooks.databinding.ActivityMainBinding
import cl.aiep.android.anchorbooks.modelo.Libro
import cl.aiep.android.anchorbooks.service.LibroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )

        // RECYCLERVIEW
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // RETROFIT
        val baseUrl = "https://my-json-server.typicode.com/Himuravidal/anchorBooks/"
        val retrofit = Retrofit.Builder()
            .baseUrl( baseUrl )
            .addConverterFactory( GsonConverterFactory.create() )
            .build()

        val libroService = retrofit.create( LibroService::class.java )

        // LISTA TODOS LOS LIBROS
        libroService.listLibros().enqueue(object : Callback<List<Libro>>{
            override fun onResponse(call: Call<List<Libro>>, response: Response<List<Libro>>) {
                val libros = response.body() ?: listOf()
                recyclerView.adapter = LibroAdapter(libros)
                Log.d("RECYCLER", "Cargando datos en el Adapter...")
            }

            override fun onFailure(call: Call<List<Libro>>, t: Throwable) {
                t.printStackTrace()
                Log.e("RETROFIT", "Falló al recuperar los libros")
            }

        })
    }
}