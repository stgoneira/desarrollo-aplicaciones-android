package cl.aiep.android.anchorbooks.service

import cl.aiep.android.anchorbooks.db.LibroDao
import cl.aiep.android.anchorbooks.helper.LibroHelper
import cl.aiep.android.anchorbooks.modelo.LibroModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class LibroRepositoryTest {

    private lateinit var libroAPI:LibroAPI
    private lateinit var libroDao:LibroDao
    private lateinit var repository:LibroRepository
    private lateinit var response: Response<List<LibroModel>>

    @Before
    fun setup() {
        libroAPI = mockk<LibroAPI>()
        libroDao = mockk<LibroDao>(relaxed = true)
        response = mockk<Response<List<LibroModel>>>()
        repository = LibroRepository(libroAPI, libroDao)
    }

    @Test
    fun `probar que se guarden en BD los datos de la API`() = runBlocking {
        // config
        every { response.body() } returns listOf(LibroHelper.emptyLibroModel())
        every { response.isSuccessful } returns true
        coEvery { libroAPI.listLibros() } returns response

        // asserts
        assertEquals(repository.findAll().size, 1)

        // checkeo de invocaciones
        coVerify(exactly = 1) { libroDao.deleteAll() }
        coVerify(exactly = 1) { libroDao.insertAll(any()) }
        coVerify(exactly = 0) { libroDao.getAll() }
    }

    @Test
    fun `probar q los datos vengan de BD cuando la API falla`() = runBlocking {
        // config
        every { response.body() } returns listOf()
        every { response.isSuccessful } returns false
        coEvery { libroAPI.listLibros() } returns response

        // asserts
        repository.findAll()

        // checkeo de invocaciones
        coVerify(exactly = 0) { libroDao.deleteAll() }
        coVerify(exactly = 0) { libroDao.insertAll(any()) }
        coVerify(exactly = 1) { libroDao.getAll() }
    }
}