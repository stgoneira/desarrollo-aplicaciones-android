package cl.stgoneira.ejemploroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.room.Room
import cl.stgoneira.ejemploroom.db.BaseDatos
import cl.stgoneira.ejemploroom.db.Producto
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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