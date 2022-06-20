import data.TareaRepository
import data.ws.TareaService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun main(args: Array<String>) {
    runBlocking {
        launch {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory( GsonConverterFactory.create() )
                .build()

            val tareaService = retrofit.create(TareaService::class.java)

            val repo = TareaRepository(tareaService)

            val tareas = repo.findAll()

            println("Tareas:")
            println(tareas.toString())
        }
    }
}