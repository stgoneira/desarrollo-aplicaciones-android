package cl.aiep.android.anchorbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.aiep.android.anchorbooks.databinding.ActivityDetalleBinding
import cl.aiep.android.anchorbooks.db.BaseDatos
import cl.aiep.android.anchorbooks.db.LibroEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetalleActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // corrutina
        GlobalScope.launch(Dispatchers.IO){
            val db = Room.databaseBuilder(
                applicationContext,
                BaseDatos::class.java,
                "libros-db"
            ).build()

            val libroDao = db.libroDao()

            libroDao.deleteAll()

            libroDao.insertAll(
                LibroEntity(
                    1,
                    "Juan Pérez",
                    "Chile",
                    "http://example.com/img.jpg",
                    "español",
                    "http://juanito.cl",
                    110,
                    "Lorem ipsum",
                    2019,
                    9_990,
                    19_990,
                    true
                ),
                LibroEntity(
                    2,
                    "Catalina Venegas",
                    "Chile",
                    "http://example.com/img2.jpg",
                    "español",
                    "http://catalina.cl",
                    220,
                    "Dolor atme",
                    2022,
                    19_990,
                    29_990,
                    false
                ),
            )

            val libro = libroDao.findById(2)
            binding.textView.text = libro.toString()
            Log.d("LIBRO-BD", libro.toString())

        }
    }
}