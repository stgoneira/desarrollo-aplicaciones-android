# Dagger Hilt

https://dagger.dev/hilt

## Configuration :: build.gradle (Project)

Agregar Dependencia del Plugin 

```
buildscript {

    dependencies {
        classpath 'com.google.dagger:hilt-android-gradle-plugin:2.42'
    }
}
```

## Configuration :: build.gradle (Module)

Agregar kapt para anotaciones Kotlin y plugin de Dagger Hilt.
Se configura también correctErrorTypes en bloque kapt.

```
plugins {
	...
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin'
}

...

kapt {
    correctErrorTypes true
}
```

## Dependencies :: build.gradle (Module)

```
// REQUERIDAS PARA HILT

// Hilt
implementation 'com.google.dagger:hilt-android:2.42'
kapt 'com.google.dagger:hilt-compiler:2.42'

// Hilt: For instrumentation tests
androidTestImplementation  'com.google.dagger:hilt-android-testing:2.42'
kaptAndroidTest 'com.google.dagger:hilt-compiler:2.42'

// Hilt: For local unit tests
testImplementation 'com.google.dagger:hilt-android-testing:2.42'
kaptTest 'com.google.dagger:hilt-compiler:2.42'

// OPCIONALES PARA EL EJEMPLO

// Coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.4.0'

// ROOM
implementation "androidx.room:room-runtime:$room_version"
kapt "androidx.room:room-compiler:$room_version"

```

## Code Example :: MiAplicacion.kt 

Extender de Application y anotar con Hilt para activar DI en el proyecto
```kotlin 
@HiltAndroidApp
class MiAplicacion : Application() 
```

## Manifest.xml
```xml
<manifest>
	...
	<application
		    android:name=".app.MiAplicacion"
		    ...
	>
	</application>
</manifest>
```

## Code Example :: BdModule.kt 

Objecto que provee una BD de Room y también un objecto DAO.
Se puede utilizar la anotación @ApplicationContext para inyectar un contexto

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object BdModule {

    @Provides
    @Singleton
    fun baseDatos(@ApplicationContext appContext:Context):BaseDatos {
        return Room
            .databaseBuilder(appContext, BaseDatos::class.java, "catalogo-db")
            .build()
    }

    @Provides
    @Singleton
    fun productoDao(bd:BaseDatos): ProductoDao = bd.productoDao()
}
```

## Code Example :: ProductoRepository.kt 

ProductoRepository es inyectable y también recibe inyectado un productoDao gracias a DaggerHilt en su constructor.

```kotlin
class ProductoRepository @Inject constructor(private val productoDao: ProductoDao) {

	fun findAll():List<ProductoEntity> = productoDao.getAll()

    fun crear(producto:ProductoEntity) = productoDao.insertAll(producto)

    fun borrarTodo() = productoDao.deleteAll()
}
```


## Code Example :: MainActivity.kt 

Se usa anotación @Inject para inyectar y hacer inyectable.
Lo inyectado estará disponible después de la invocación al super en método onCreate(). 
La Actividad debe ser anotada con @AndroidEntryPoint

```kotlin
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var productoRepository: ProductoRepository

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // IO para trabajo de Entrada/Salida
        lifecycleScope.launch(Dispatchers.IO) {
            // acá se puede utilizar la variable inyectada
            productoRepository.borrarTodo()

            productoRepository.crear(ProductoEntity(1, "Plato", 2_000.0))
            productoRepository.crear(ProductoEntity(2, "Cuchara", 500.0))

            val productos = productoRepository.findAll()

            // Main para escribir en UI
            withContext(Dispatchers.Main) {
                binding.textview.text = productos.toString()
            }
        }
    }
}
```
