package cl.aiep.android.anchorbooks.service

import cl.aiep.android.anchorbooks.db.LibroDao
import cl.aiep.android.anchorbooks.db.LibroHelper
import cl.aiep.android.anchorbooks.modelo.LibroModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyObject
import retrofit2.Response

class LibroRepositoryTest {

    @RelaxedMockK
    private lateinit var libroDao:LibroDao
    @RelaxedMockK
    private lateinit var libroAPI: LibroAPI

    @RelaxedMockK
    private lateinit var respuestaError:Response<List<LibroModel>>

    @RelaxedMockK
    private lateinit var respuestaExito:Response<List<LibroModel>>

    private lateinit var repository:LibroRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        repository = LibroRepository(libroAPI, libroDao)
        respuestaError = Response.error(500, ResponseBody.create(MediaType.parse("application/json"), ""))
        respuestaExito = Response.success(200, listOf(LibroHelper.emptyLibroModel()))
    }

    @Test
    fun `cuando funcione la API, debe cachear en BD`() = runBlocking {
        // Given - Dado
        coEvery { libroAPI.listLibros() } returns respuestaExito

        // When - Cuando
        repository.findAll()

        // Then - Entonces
        coVerify(exactly = 1) { libroDao.deleteAll() }
        coVerify(exactly = 1) { libroDao.insertAll(any()) }
        coVerify(exactly = 0) { libroDao.getAll() }
    }


    @Test
    fun `cuando falle la API, debe consultar la BD`() = runBlocking {
        // Given - Dado
        coEvery { libroAPI.listLibros() } returns respuestaError

        // When - Cuando
        repository.findAll()

        // Then - Entonces
        coVerify(exactly = 1) { libroDao.getAll() }
        coVerify(exactly = 0) { libroDao.deleteAll() }
    }

}