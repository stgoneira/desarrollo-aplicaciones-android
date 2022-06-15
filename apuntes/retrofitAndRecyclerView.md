# Retrofit and RecyclerView 

API example: https://jsonplaceholder.typicode.com/photos 

## Example Data 

```json
[
  {
    "albumId": 1,
    "id": 1,
    "title": "accusamus beatae ad facilis cum similique qui sunt",
    "url": "https://via.placeholder.com/600/92c952",
    "thumbnailUrl": "https://via.placeholder.com/150/92c952"
  },
  {
    "albumId": 1,
    "id": 2,
    "title": "reprehenderit est deserunt velit ipsam",
    "url": "https://via.placeholder.com/600/771796",
    "thumbnailUrl": "https://via.placeholder.com/150/771796"
  }
]
```

## Permisos :: AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Dependencias :: build.gradle

```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

implementation 'com.squareup.picasso:picasso:2.8'
```

## Model 

```kotlin
import com.google.gson.annotations.SerializedName

data class Foto (
    val albumId:Int
    , val id:Int
    , @SerializedName("title")          val titulo:String
    , @SerializedName("url")            val imgUrl:String
    , @SerializedName("thumbnailUrl")   val miniatura:String
)
```

## Service 

```kotlin
import retrofit2.Call
import retrofit2.http.GET

interface FotoService {

    @GET("photos")
    fun listFotos(): Call<List<Foto>>

}
```

## Adapter 

```kotlin 
class FotoAdapter(private val datos:List<Foto>):RecyclerView.Adapter<FotoAdapter.ViewHolder>() {

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val textView:TextView
        val imageView:ImageView

        init {
            textView    = view.findViewById(R.id.textView)
            imageView   = view.findViewById(R.id.imageView)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from( viewGroup.context ).inflate(R.layout.foto_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foto = datos.get(position)
        holder.textView.text = foto.titulo
        Picasso.get().load(foto.miniatura).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}
```

## Ejemplo Adapter con ViewBinding

```kotlin 
class LibroAdapter(private val datos:List<Libro>):RecyclerView.Adapter<LibroAdapter.ViewHolder>() {

    class ViewHolder(val binding:LibroItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LibroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val libro = datos.get(position)
        with(holder.binding) {
            tvTitulo.text   = libro.titulo
            tvAutor.text    = "Autor: ${libro.autor}"
            tvLenguaje.text = "Lenguaje: ${libro.lenguaje}"
            tvPais.text     = "País: ${libro.pais}"
            Picasso.get().load(libro.imagen).into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}
```

## Activity 

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RECYCLER VIEW
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // RETROFIT
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val fotoService = retrofit.create(FotoService::class.java)

        val callFotos = fotoService.listFotos()
        callFotos.enqueue( object: Callback<List<Foto>> {
            override fun onResponse(call: Call<List<Foto>>, response: Response<List<Foto>>) {
                val fotos = response.body() ?: listOf()
                recyclerView.adapter = FotoAdapter(fotos)
                Log.d("MIAPP", "cargando datos ...")
            }

            override fun onFailure(call: Call<List<Foto>>, t: Throwable) {
                Log.e("RETROFIT", "Retrofit falló al traer las fotos")
            }
        } )
    }
}
```

## Layout :: activity_main.xml 

```xml 
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
    />

</androidx.constraintlayout.widget.ConstraintLayout>
```

## Layout :: foto_item.xml 

IMPORTANTE: height -> wrap_content

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:srcCompat="@tools:sample/avatars" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="32dp"
        android:layout_marginTop="16dp"
        tools:text="textView"
        app:layout_constraintStart_toEndOf="@+id/imageView"
        app:layout_constraintTop_toTopOf="parent" />
		
</androidx.constraintlayout.widget.ConstraintLayout>
```
