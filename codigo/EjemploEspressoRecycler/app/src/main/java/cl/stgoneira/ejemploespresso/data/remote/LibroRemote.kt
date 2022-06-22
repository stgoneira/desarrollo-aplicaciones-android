package cl.stgoneira.ejemploespresso.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LibroRemote {

    @GET("books")
    suspend fun findAll():Response<List<Libro>>

    @GET("bookDetail/{id}")
    suspend fun findById(@Path("id") libroId:Int):Response<Libro>

}