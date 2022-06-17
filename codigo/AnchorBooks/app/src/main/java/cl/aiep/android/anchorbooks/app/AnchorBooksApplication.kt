package cl.aiep.android.anchorbooks.app

import android.app.Application
import androidx.room.Room
import cl.aiep.android.anchorbooks.db.BaseDatos
import cl.aiep.android.anchorbooks.service.LibroAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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