package cl.aiep.android.anchorbooks.service

import androidx.room.Room
import cl.aiep.android.anchorbooks.db.BaseDatos
import cl.aiep.android.anchorbooks.db.LibroDao
import cl.aiep.android.anchorbooks.modelo.Libro
import cl.aiep.android.anchorbooks.modelo.LibroModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LibroRepository {

    val libroAPI:LibroAPI
    val libroDao:LibroDao

    init {
        // Retrofit
        val baseUrl = "https://my-json-server.typicode.com/Himuravidal/anchorBooks/"
        val retrofit = Retrofit.Builder()
            .baseUrl( baseUrl )
            .addConverterFactory( GsonConverterFactory.create() )
            .build()
        libroAPI = retrofit.create( LibroAPI::class.java )

        // LibroDao
        val db = Room.databaseBuilder(null, BaseDatos::class.java, "libros-bd").build()
        libroDao = db.libroDao()
    }

    suspend fun findAll():List<Libro> {
        return withContext(Dispatchers.IO) {
            val response = libroAPI.listLibros()
            if( response.code() == 200) {
                response.body() ?: emptyList()
            }else {

            }
        }
    }

}