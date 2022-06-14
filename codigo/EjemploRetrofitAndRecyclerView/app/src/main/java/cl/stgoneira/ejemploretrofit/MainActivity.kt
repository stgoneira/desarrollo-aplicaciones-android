package cl.stgoneira.ejemploretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.stgoneira.ejemploretrofit.model.Foto
import cl.stgoneira.ejemploretrofit.service.FotoService
import cl.stgoneira.ejemploretrofit.ui.recycler.FotoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RECYCLER VIEW
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // RETROFIT
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val fotoService = retrofit.create(FotoService::class.java)

        val callFotos = fotoService.listFotos()
        callFotos.enqueue( object: Callback<List<Foto>> {
            override fun onResponse(call: Call<List<Foto>>, response: Response<List<Foto>>) {
                val fotos = response.body() ?: listOf()
                recyclerView.adapter = FotoAdapter(fotos)
                Log.d("MIAPP", "cargando datos ...")
            }

            override fun onFailure(call: Call<List<Foto>>, t: Throwable) {
                Log.e("RETROFIT", "Retrofit falló al traer las fotos")
            }

        } )


    }
}