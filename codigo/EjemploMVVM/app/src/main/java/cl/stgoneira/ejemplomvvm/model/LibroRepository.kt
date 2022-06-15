package cl.stgoneira.ejemplomvvm.model

import cl.stgoneira.ejemplomvvm.model.api.LibroAPI
import cl.stgoneira.ejemplomvvm.model.api.LibroModel
import cl.stgoneira.ejemplomvvm.model.db.Libro
import cl.stgoneira.ejemplomvvm.model.db.LibroDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LibroRepository {

    val libroAPI:LibroAPI

    init {
        libroAPI = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/Himuravidal/anchorBooks/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibroAPI::class.java)
    }

    suspend fun findAll():List<LibroModel> {
        return withContext(Dispatchers.IO) {
            val response = libroAPI.listLibros()
            response.body() ?: emptyList()
        }
    }
}