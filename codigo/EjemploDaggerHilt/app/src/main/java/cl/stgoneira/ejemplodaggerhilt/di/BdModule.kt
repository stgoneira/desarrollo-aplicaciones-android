package cl.stgoneira.ejemplodaggerhilt.di

import android.content.Context
import androidx.room.Room
import cl.stgoneira.ejemplodaggerhilt.data.BaseDatos
import cl.stgoneira.ejemplodaggerhilt.data.dao.ProductoDao
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
            .databaseBuilder(appContext, BaseDatos::class.java, "catalogo-db")
            .build()
    }

    @Provides
    @Singleton
    fun productoDao(bd:BaseDatos): ProductoDao = bd.productoDao()
}