package data.ws

import retrofit2.Response
import retrofit2.http.GET

interface TareaService {

    @GET("todos")
    suspend fun tareas():Response<List<Tarea>>

}