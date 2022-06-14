package cl.stgoneira.ejemploretrofit.service

import cl.stgoneira.ejemploretrofit.model.Foto
import retrofit2.Call
import retrofit2.http.GET

interface FotoService {

    @GET("photos")
    fun listFotos(): Call<List<Foto>>

}