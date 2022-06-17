# Caso Anchor Books

## Permisos :: AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Plugins :: build.gradle (Project)

```
plugins {
	...
    id "org.jetbrains.kotlin.kapt" version "1.7.0" apply false
    ...
}

```

## Plugins :: build.gradle (Module)
```
plugins {
	...
    id 'org.jetbrains.kotlin.kapt'
    ...
}
```

## ViewBinding :: build.gradle (Module)
```
android {
	...
	buildFeatures {
		viewBinding true
	}
	...
}
```

## Dependencias :: build.gradle (Module)

```
dependencies {
    def lifecycle_version = "2.4.1"
    def room_version = "2.4.2"

    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.2'
    implementation 'com.google.android.material:material:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'

    // ROOM
    implementation "androidx.room:room-runtime:$room_version"
    kapt "androidx.room:room-compiler:$room_version"

    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.4.0'

    // ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    implementation "androidx.activity:activity-ktx:1.4.0"
    implementation "androidx.fragment:fragment-ktx:1.4.1"

    // LiveData
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"

    // Annotation processor
    kapt "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"

    // picasso
    implementation 'com.squareup.picasso:picasso:2.8'

    // retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

    // Testing Unitario
    testImplementation 'junit:junit:4.+'
    testImplementation 'io.mockk:mockk:1.12.2'
    testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0"
    testImplementation "androidx.arch.core:core-testing:2.1.0"

    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}
```

## Libro Interface 

```kotlin 
interface Libro {
    val id: Int
    val autor: String
    val pais: String
    val imagen: String
    val lenguaje: String
    val enlace: String?
    val paginas: Int?
    val titulo: String
    val anno: Int?
    val precio: Int?
    val ultimoPrecio: Int?
    val despacho: Boolean?
}
```

## LibroEntity 

```kotlin 
@Entity(tableName = "libros")
data class LibroEntity(
    @PrimaryKey override val id:Int,
    override val autor:String,
    override val pais:String,
    override val imagen:String,
    override val lenguaje:String,
    override val enlace:String?,
    override val paginas:Int?,
    override val titulo:String,
    override val anno:Int?,
    override val precio:Int?,
    override val ultimoPrecio:Int?,
    override val despacho:Boolean?
):Libro
```

## LibroDAO 

```kotlin 
@Dao
interface LibroDao {

    @Query("SELECT * FROM libros")
    fun getAll() : List<LibroEntity>

    @Query("SELECT * FROM libros WHERE id = :lid")
    fun findById(lid:Int):LibroEntity

    @Query("DELETE FROM libros")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg libros:LibroEntity)

    @Delete
    fun delete(libro:LibroEntity)
}
```

## RoomBD 

```kotlin
@Database(entities = [LibroEntity::class], version = 1)
abstract class BaseDatos :RoomDatabase() {
    abstract fun libroDao():LibroDao
}
```


## API Service Interface

```kotlin
interface LibroAPI {

    @GET("books")
    suspend fun listLibros(): Response<List<LibroModel>>

    @GET("bookDetail/{id}")
    suspend fun detailLibro(@Path("id") libroId:Int): Response<LibroModel>

}
```

## Repository 

```kotlin 
class LibroRepository (
    val libroAPI:LibroAPI,
    val libroDao:LibroDao
) {

    suspend fun findById(id:Int):Libro {
        return withContext(Dispatchers.IO) {
            val response = libroAPI.detailLibro(id)
            if( response.isSuccessful ) {
                val libro = response.body() ?: LibroHelper.emptyLibroEntity()
                libroDao.insertAll(LibroMapper.toEntity(libro))
                libro
            } else {
                libroDao.findById(id)
            }
        }
    }

    suspend fun findAll():List<Libro> {
        return withContext(Dispatchers.IO) {
            val response = libroAPI.listLibros()
            if( response.isSuccessful ) {
                val libros = response.body() ?: emptyList()
                libroDao.deleteAll()
                for(libro in libros) {
                    libroDao.insertAll( LibroMapper.toEntity(libro) )
                }
                libros
            } else {
                libroDao.getAll()
            }
        }
    }
}
```

## Application 

```kotlin 
class AnchorBooksApplication : Application() {

    val baseDatos by lazy {
        Room.databaseBuilder(
            this,
            BaseDatos::class.java,
            "libros-db"
        ).build()
    }

    val libroDao by lazy { baseDatos.libroDao() }

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/Himuravidal/anchorBooks/")
            .addConverterFactory( GsonConverterFactory.create() )
            .build()
    }

    val libroAPI by lazy {retrofit.create(LibroAPI::class.java)}

}
```

## Manifest for Custom Application

```kotlin 
<manifest>
	...
	<application
			android:name=".app.AnchorBooksApplication"
			...
	/>
	...
</manifest>
```

## ViewModel

```kotlin
class LibroViewModel(application:Application): AndroidViewModel(application) {

    val libros = MutableLiveData<List<Libro>>()

    fun cargarLibros() {
        viewModelScope.launch {
            val app = getApplication<AnchorBooksApplication>()
            val repo = LibroRepository(app.libroAPI, app.libroDao)
            val librosFromRepo = repo.findAll()
            if( !librosFromRepo.isNullOrEmpty() ) {
                libros.postValue(librosFromRepo)
            }
        }
    }
}
```

## ViewModel

```kotlin
class LibroDetalleViewModel(application: Application) : AndroidViewModel(application) {

    val libro = MutableLiveData<Libro>()

    fun cargarLibro(libroId:Int) {
        viewModelScope.launch {
            val app = getApplication<AnchorBooksApplication>()
            val repo = LibroRepository(app.libroAPI, app.libroDao)
            val libroFromRepo = repo.findById(libroId)
            libro.postValue(libroFromRepo)
        }
    }
}
```

## MainActivity.kt

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private val libroViewModel : LibroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView( binding.root )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        libroViewModel.cargarLibros()

        libroViewModel.libros.observe(this, Observer { libros ->
            binding.recyclerView.adapter = LibroAdapter(libros)
        })
    }
}
```

## Constraint Layout Guide

```xml 
<androidx.constraintlayout.widget.Guideline
        android:id="@+id/guidelineHor50"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.4   " />
``` 

## Constraint Layout - View Background - Attached to Guide - height 0

```xml 
<View
        android:id="@+id/viewBgAccent"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:background="@color/accent_color"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toTopOf="@id/guidelineHor50"
        />
```

## Mappers

```kotlin 
class LibroMapper {
    companion object {
        fun toEntity(libro:Libro):LibroEntity {
            with(libro){
                return LibroEntity(
                    id, autor, pais, imagen, lenguaje, enlace, paginas, titulo, anno, precio, ultimoPrecio, despacho
                )
            }
        }
    }
}
``` 
 

## Helpers 

```kotlin 
class LibroHelper {
    companion object {
        fun emptyLibroEntity():LibroEntity {
            return LibroEntity(
                0,
                "S/I",
                "S/I",
                "https://via.placeholder.com/140x100",
                "S/I",
                "S/I",
                0,
                "S/I",
                0,
                0,
                0,
                false
            )
        }

        fun emptyLibroModel():LibroModel {
            return LibroModel(
                0,
                "S/I",
                "S/I",
                "https://via.placeholder.com/140x100",
                "S/I",
                "S/I",
                0,
                "S/I",
                0,
                0,
                0,
                false
            )
        }
    }
}
```

## MainActivity 

```kotlin 
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val libroViewModel:LibroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )

        // RECYCLERVIEW
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LibroAdapter(emptyList())

        libroViewModel.cargarLibros()

        libroViewModel.libros.observe(this, Observer { libros ->
            binding.recyclerView.adapter = LibroAdapter(libros)
        })
    }
}
```

## DetalleActivity 

```kotlin
class DetalleActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetalleBinding

    private val libroDetalleViewModel: LibroDetalleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val libroId = intent.getIntExtra(LIBROID_MESSAGE, 0)

        libroDetalleViewModel.cargarLibro(libroId)

        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.currency = Currency.getInstance("CLP")
        numberFormat.maximumFractionDigits = 0

        libroDetalleViewModel.libro.observe(this, Observer {    libro ->
            with(binding) {
                tvDetalleTitulo.text = libro.titulo
                tvDetalleAutor.text = libro.autor
                tvDetallePrecio.text = numberFormat.format(libro.precio)
                Picasso.get().load(libro.imagen).into(imgDetalleLibro)

                btnDetalleComprar.setOnClickListener {
                    val textoCorreo = """
                        Hola,
                        
                        Vi el libro ${libro.titulo} de código ${libro.id} y me gustaría que me contactaran a este correo o al siguiente número _________.
                        
                        Quedo atento.
                    """.trimIndent()
                    val intentMail = Intent(Intent.ACTION_SENDTO).apply {
                        type = "msage/rfc822"
                        data = Uri.parse("mailto:")
                        val para:Array<String> = arrayOf("ventas@anchorbooks.cl")
                        putExtra(Intent.EXTRA_EMAIL, para)
                        putExtra(Intent.EXTRA_SUBJECT, "Consulta por libro ${libro.titulo} id ${libro.id}")
                        putExtra(Intent.EXTRA_TEXT, textoCorreo)
                    }

                    startActivity(intentMail)
                }
            }
        })
    }
}
```