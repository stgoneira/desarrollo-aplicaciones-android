package data

import data.ws.Tarea
import data.ws.TareaService

class TareaRepository(
    val tareaService: TareaService
) {

    suspend fun findAll():List<Tarea> {
        val tareasResponse = tareaService.tareas()
        if( tareasResponse.isSuccessful ) {
            return tareasResponse.body() ?: emptyList()
        }
        throw java.lang.RuntimeException("Falló Retrofit")
    }

}