# Model View ViewModel 

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
def lifecycle_version = "2.4.1"
def room_version = "2.4.2"

// retrofit
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

// picasso
implementation 'com.squareup.picasso:picasso:2.8'

// ROOM
implementation "androidx.room:room-runtime:$room_version"
kapt "androidx.room:room-compiler:$room_version"

// Coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'

// ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
implementation "androidx.activity:activity-ktx:1.4.0"
implementation "androidx.fragment:fragment-ktx:1.4.1"

// LiveData
implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"

// Annotation processor
kapt "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
```


## API Service Interface

```kotlin
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LibroAPI {

    @GET("books")
    suspend fun listLibros(): Response<List<LibroModel>>

    @GET("bookDetail/{id}")
    suspend fun detailLibro(@Path("id") libroId:Int): Response<LibroModel>

}
```

## ViewModel

```kotlin
class LibroViewModel:ViewModel() {

    val libros = MutableLiveData<List<LibroModel>>()

    fun cargarLibros() {
        viewModelScope.launch {
            val repo = LibroRepository()
            val librosFromRepo = repo.findAll()
            if(!librosFromRepo.isNullOrEmpty()) {
                libros.postValue(librosFromRepo)
            }
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


