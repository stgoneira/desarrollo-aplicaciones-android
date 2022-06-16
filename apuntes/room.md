# ROOM

https://developer.android.com/training/data-storage/room

## Configuration :: build.gradle (Project)

Agregar Plugin KAPT

```
plugins {
	...
    id "org.jetbrains.kotlin.kapt" version "1.7.0" apply false
    ...
}
```

## Configuration :: build.gradle (Module)

```
plugins {
	...
    id 'org.jetbrains.kotlin.kapt'
    ...
}
```

## Dependencies :: build.gradle (Module)

```
// ROOM
implementation "androidx.room:room-runtime:$room_version"
kapt "androidx.room:room-compiler:$room_version"

// Coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
```

## Code Example :: Producto.kt 

```kotlin 
@Entity(tableName = "productos")
data class Producto (
    @PrimaryKey val id:Int,
    @ColumnInfo(name="nombre") val producto:String,
    val precio:Double
)
```

## Code Example :: ProductoDao.kt 

```kotlin 
@Dao
interface ProductoDao {

    @Query("SELECT * FROM productos")
    fun getAll(): List<Producto>

    @Query("SELECT * FROM productos WHERE id = :pid")
    fun findById(pid:Int):Producto

    @Insert
    fun insertAll(vararg productos:Producto)

    @Delete
    fun delete(producto:Producto)

}
```

## Code Example :: BaseDatos.kt 

```kotlin 
@Database(entities = [Producto::class], version = 1)
abstract class BaseDatos : RoomDatabase() {
    abstract fun productoDao():ProductoDao
}
```

## Code Example :: MainActivity.kt 

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // coroutine
        GlobalScope.launch {
            val db = Room.databaseBuilder(
                applicationContext,
                BaseDatos::class.java, "catalogo-db"
            ).build()

            val productoDao = db.productoDao()

            productoDao.insertAll(
                Producto(1, "Correa Paseo", 9990.0),
                Producto(2, "Pelota Juguete", 3990.0)
            )

            val productos = productoDao.getAll()

            val textview = findViewById<TextView>(R.id.textview)
            textview.text = productos.toString()
        }//end coroutine
    }
}
```
