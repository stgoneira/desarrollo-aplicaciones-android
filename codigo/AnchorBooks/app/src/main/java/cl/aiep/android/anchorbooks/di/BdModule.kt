package cl.aiep.android.anchorbooks.di

import android.content.Context
import androidx.room.Room
import cl.aiep.android.anchorbooks.db.BaseDatos
import cl.aiep.android.anchorbooks.db.LibroDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BdModule {

    @Provides
    @Singleton
    fun baseDatos(@ApplicationContext appContext:Context):BaseDatos {
        return Room
            .databaseBuilder(
                appContext,
                BaseDatos::class.java,
                "libros-db"
            )
            .build()
    }

    @Provides
    @Singleton
    fun libroDao(baseDatos: BaseDatos):LibroDao {
        return baseDatos.libroDao()
    }

}