package cl.stgoneira.ejemploretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import cl.stgoneira.ejemploretrofit.model.Foto
import cl.stgoneira.ejemploretrofit.service.FotoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val fotoService = retrofit.create(FotoService::class.java)

        val callFotos = fotoService.listFotos()
        callFotos.enqueue( object: Callback<List<Foto>> {
            override fun onResponse(call: Call<List<Foto>>, response: Response<List<Foto>>) {
                val fotos = response.body()
                val textView = findViewById<TextView>(R.id.textview)
                textView.text = fotos.toString()
            }

            override fun onFailure(call: Call<List<Foto>>, t: Throwable) {
                Log.e("RETROFIT", "Retrofit falló al traer las fotos")
            }

        } )


    }
}