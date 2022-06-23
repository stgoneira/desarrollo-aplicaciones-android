package cl.aiep.android.anchorbooks.app

import android.app.Application
import androidx.room.Room
import cl.aiep.android.anchorbooks.db.BaseDatos
import cl.aiep.android.anchorbooks.service.LibroAPI
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class AnchorBooksApp : Application()