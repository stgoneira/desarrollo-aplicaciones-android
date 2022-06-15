package cl.aiep.android.anchorbooks.service

import cl.aiep.android.anchorbooks.modelo.Libro
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LibroService {

    @GET("books")
    fun listLibros(): Call<List<Libro>>

    @GET("bookDetail/{id}")
    fun detailLibro(@Path("id") libroId:Int): Call<Libro>

}