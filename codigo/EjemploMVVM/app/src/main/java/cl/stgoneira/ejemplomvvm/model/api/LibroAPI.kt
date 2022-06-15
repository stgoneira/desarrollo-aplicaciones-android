package cl.stgoneira.ejemplomvvm.model.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LibroAPI {

    @GET("books")
    suspend fun listLibros(): Response<List<LibroModel>>

    @GET("bookDetail/{id}")
    suspend fun detailLibro(@Path("id") libroId:Int): Response<LibroModel>

}