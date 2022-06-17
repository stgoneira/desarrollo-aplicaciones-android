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

class LibroRepository (
    val libroAPI:LibroAPI,
    val libroDao:LibroDao
) {

    suspend fun findAll():List<Libro> {
        return withContext(Dispatchers.IO) {
            val response = libroAPI.listLibros()
            //if( response.code() == 200) {
            response.body() ?: emptyList()
        }
    }
}