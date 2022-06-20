package data

import data.ws.Tarea
import data.ws.TareaService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

internal class TareaRepositoryTest {
    lateinit var tareaRepository:TareaRepository
    lateinit var tareaService: TareaService
    lateinit var response:Response<List<Tarea>>

    @BeforeEach
    fun setup() {
        tareaService = mockk<TareaService>()
        response = mockk<Response<List<Tarea>>>()
        tareaRepository = TareaRepository(tareaService)
    }

    @Test
    fun tareas() = runBlocking {
        every { response.body() } returns listOf(Tarea(3,1,"Lavar", false))
        every { response.isSuccessful } returns true

        coEvery { tareaService.tareas() } returns response

        assertEquals(tareaRepository.findAll().size, 1)

        coVerify(exactly = 1) { response.body() }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun tareasError() = runBlocking {
        every { response.isSuccessful } returns false
        every { response.body() } returns emptyList()

        coEvery { tareaService.tareas() } returns response

        // https://kotlinlang.org/docs/exception-handling.html#exception-propagation
        // GlobalScope necesario para que la corutina propague la Exception a la raíz
        val job = GlobalScope.async { tareaRepository.findAll() }
        val exception = org.junit.jupiter.api.assertThrows<java.lang.RuntimeException> {
            job.await()
        }
        assertEquals(exception?.message, "Falló Retrofit")

        coVerify(exactly = 0) { response.body() }
    }

}