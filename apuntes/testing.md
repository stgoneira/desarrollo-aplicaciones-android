# Testing

## Dependencies :: build.gradle (Module)

```
testImplementation 'junit:junit:4.+'
testImplementation 'io.mockk:mockk:1.12.2'
testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0"
testImplementation "androidx.arch.core:core-testing:2.1.0"
```

## Code Example :: MiAplicacion.kt 

Para generar una clase de Test nos podemos ayudar con AndroidStudio (Navigate -> Test o CTRL + SHIFT + T).
En este ejemplo se utiliza la librería Mockk para generar algunos mocks para probar que la clase repositorio utilice los datos desde la API (network) en caso que todo ande bien, y en caso que se produzca algún problema utilizar ROOM para traer los datos desde SQLite.

```kotlin 
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
```

## Otro ejemplo 

```kotlin
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
```

